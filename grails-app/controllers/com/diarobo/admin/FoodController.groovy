package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.MasterKeySetup
import com.diarobo.Picture
import com.diarobo.User
import com.diarobo.commands.FoodLibraryCommand
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.GroupItems
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

import javax.servlet.http.HttpServletRequest

@Secured(CommonUtils.ROLE_ADMIN)
class FoodController {

    def groupItemService
    def masterKeyService
    def springSecurityService
    def pictureService
    def foodService
    def messageSource

    def index() {}

    def create(){
        def netWeightUnits = masterKeyService.keyDropDownList(MasterKeyValue.FWU.value)
        def measurementUnits = masterKeyService.keyDropDownList(MasterKeyValue.FMU.value)
        render(view: 'create', model: [netWeightUnits: netWeightUnits, measurementUnits: measurementUnits])
    }
    def save(FoodLibraryCommand command){
        switch (request.method) {
            case 'GET':
                redirect(action: 'create')
                return
                break
            case 'POST':
                if (command.hasErrors()) {
                    flash.error = "Please Fill required fields"
                    redirect(action: 'create')
                    return
                    break
                }
                GroupItem groupItem = groupItemService.findOrCreateFirstNode(GroupItems.FOOD.key, command.foodGroup)
                if(groupItem) {
                    FoodLibrary food = FoodLibrary.findByName(params.name)
                    if(!food) {
                        food = new FoodLibrary(groupItem: groupItem, approximateWeight: params.getDouble('approximateWeight'), name: params.name, measureUnit: params.measureUnit,
                                weightMeasure: params.weightMeasure)
                        FoodLibrary savedItem =food.save()
                        if (savedItem) {
                            Nutrition nutrition
                            if(params.nutritions){
                                if(params.nutritions instanceof String){
                                    new Nutrition(name: params.nutritions, foodLibraryId: food.id).save()
                                } else {
                                    def nutritionList = params.nutritions as List
                                    nutritionList.each{it ->
                                        nutrition = new Nutrition(name: it, foodLibraryId: food.id).save()
                                    }
                                }
                            }
                            Composition composition
                            if(params.compositions){
                                if(params.compositions instanceof String){
                                    def compCount=params.compositions
                                    new Composition(name: params."compositionName${compCount}",
                                        quantity: params."compositionWeight${compCount}" , foodLibraryId: food.id).save()
                                }else {
                                    def compositionList = params.compositions as List
                                    compositionList.each{it ->
                                        composition = new Composition(name: params."compositionName${it}",
                                            quantity: params."compositionWeight${it}" , foodLibraryId: food.id).save()
                                    }
                                }
                            }
                            def file = request.getFile('foodImage')
                            if(file) {
                                if(pictureService.uploadUnSucessful(savedItem.id, 'food_library', 'library',file)) {
                                    flash.error = "Upload Error."
                                    redirect(action: 'create')
                                    return
                                    break
                                }
                            }
                        }
                        flash.message = "Successfully Saved."
                        redirect(action: 'create')
                        return
                        break
                    } else {
                        flash.error = "Already Added"
                        redirect(action: 'create')
                        return
                        break
                    }
                } else {
                    flash.error = "Group Item is not Created."
                    redirect(action: 'create')
                    return
                    break
                }
        }
    }

    def update(FoodLibraryCommand command) {
        if (command.hasErrors()) {
            flash.error = "Please Fill required fields"
            redirect(action: 'create')
            return
        }
        GroupItem groupItem = groupItemService.findOrCreateFirstNode(GroupItems.FOOD.key, command.foodGroup)
        if(groupItem) {
            FoodLibrary food = FoodLibrary.findByName(params.name)
            if(food) {
                food.approximateWeight = params.getDouble('approximateWeight')
                food.measureUnit = params.measureUnit
                food.weightMeasure = params.weightMeasure
                FoodLibrary savedItem =food.save(flush: true)
                if (savedItem) {
                    ArrayList<Nutrition> nutritionArrayList = new ArrayList<Nutrition>()
                    Nutrition nutrition
                    if(params.nutritions){
                        if(params.nutritions instanceof String){
                            nutrition = Nutrition.findByNameAndFoodLibraryId(params.nutritions, food.id)
                            if(!nutrition) {
                                nutrition = new Nutrition(name: params.nutritions, foodLibraryId: food.id).save()
                            } else {
                                nutrition.activeStatus = ActiveStatus.ACTIVE
                                nutrition.save()
                            }
                            nutritionArrayList.add(nutrition)
                        } else {
                            def nutritionList = params.nutritions as List
                            nutritionList.each{it ->
                                nutrition = Nutrition.findByNameAndFoodLibraryId(it, food.id)
                                if(!nutrition) {
                                    nutrition = new Nutrition(name: it, foodLibraryId: food.id).save()
                                } else {
                                    nutrition.activeStatus = ActiveStatus.ACTIVE
                                    nutrition.save()
                                }
                                nutritionArrayList.add(nutrition)
                            }
                        }
                        foodService.deleteNutrationUnnecessary(nutritionArrayList)
                    }
                    ArrayList<Composition> compositionArrayList = new ArrayList<Composition>()
                    Composition composition
                    if(params.compositions){
                        if(params.compositions instanceof String){
                            def compCount=params.compositions
                            composition = Composition.findByNameAndFoodLibraryId(params."compositionName${compCount}", food.id)
                            if(!composition) {
                                composition =  new Composition(name: params."compositionName${compCount}",
                                        quantity: params."compositionWeight${compCount}" , foodLibraryId: food.id).save()
                            } else {
                                composition.quantity = Double.parseDouble(params."compositionWeight${compCount}")
                                composition.activeStatus = ActiveStatus.ACTIVE
                                composition.save()
                            }
                            compositionArrayList.add(composition)
                        }else {
                            def compositionList = params.compositions as List
                            compositionList.each{it ->
                                composition = Composition.findByNameAndFoodLibraryId(params."compositionName${it}", food.id)
                                if(!composition) {
                                    composition = new Composition(name: params."compositionName${it}",
                                            quantity: params."compositionWeight${it}" , foodLibraryId: food.id).save()
                                } else {
                                    composition.quantity = Double.parseDouble(params."compositionWeight${it}")
                                    composition.activeStatus = ActiveStatus.ACTIVE
                                    composition.save()
                                }
                                compositionArrayList.add(composition)
                            }
                        }
                        foodService.deleteCompositionUnnecessary(compositionArrayList)
                    }
                    def file = request.getFile('foodImage')
                    if(file.filename) {
                        if(pictureService.uploadUnSucessful(savedItem.id, 'food_library', 'library',file)) {
                            flash.error = "Upload Error."
                            redirect(action: 'create')
                            return
                        }
                    }
                }
                flash.message = "Successfully Updated."
                redirect(action: 'index')
                return
            } else {
                flash.error = "Not FOund"
                redirect(action: 'create')
                return
            }
        } else {
            flash.error = "Group Item is not Created."
            redirect(action: 'create')
            return
        }
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap =foodService.paginationList(params)

        if(!resultMap || resultMap.totalCount== 0){
            gridData = [iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: []]
            result = gridData as JSON
            render result
            return
        }
        int totalCount =resultMap.totalCount
        gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: resultMap.results]
        result = gridData as JSON
        render result
    }


    def searchNameByKeyword() {
        def outPut = groupItemService.firstNodeNameByKeyword(params.q, GroupItems.FOOD.key)
        render outPut
    }

    def searchFoodName() {
        def outPut = groupItemService.searchFoodName(params.q)
        render outPut
    }

    def searchNutritionName(){
        def outPut = groupItemService.searchNutritionName(params.q)
        render outPut
    }

    def searchCompositionName(){
        def outPut = groupItemService.searchCompositionName(params.q)
        render outPut
    }

    def foodList(){

     }
    def foodDetails(Long id){
        FoodLibrary foodLibrary = FoodLibrary.read(id)
        if (!foodLibrary) {
            redirect(action: 'index')
            return
        }
        List<Nutrition> nutritionList = Nutrition.findAllByFoodLibraryIdAndActiveStatus(foodLibrary.id, ActiveStatus.ACTIVE)
        List<Composition> compositionList = Composition.findAllByFoodLibraryIdAndActiveStatus(foodLibrary.id, ActiveStatus.ACTIVE)
        MasterKeySetup weightUnit =  masterKeyService.masterKey(MasterKeyValue.FWU.key, foodLibrary.weightMeasure)
        render (view: "/food/foodDetails", model: [foodLibrary:foodLibrary, weightUnit: weightUnit?.keyValue, nutritionList: nutritionList, compositionList: compositionList])
    }

    def delete(Long id) {
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        FoodLibrary foodLibrary = FoodLibrary.get(id)
        if (!foodLibrary) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        foodLibrary.activeStatus=ActiveStatus.DELETED
        foodLibrary.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "FoodLibrary deleted successfully.")
        outPut = result as JSON
        render outPut
    }


    def edit(Integer foodId) {
        if (!foodId) {
            redirect(action: 'index')
            return
        }
        FoodLibrary foodLibraryInstance = FoodLibrary.findById(foodId)
        if (!foodLibraryInstance) {
            redirect(action: 'index')
            return
        }
        def measurementUnits = masterKeyService.keyDropDownList(MasterKeyValue.FMU.value)
        def netWeightUnits = masterKeyService.keyDropDownList(MasterKeyValue.FWU.value)
        def nutritionList = Nutrition.findAllByFoodLibraryIdAndActiveStatus(foodId, ActiveStatus.ACTIVE)
        def compositionList = Composition.findAllByFoodLibraryIdAndActiveStatus(foodId, ActiveStatus.ACTIVE)

        render(view: 'edit', model: [measurementUnits: measurementUnits, netWeightUnits: netWeightUnits,
                                     foodLibraryInstance: foodLibraryInstance, nutritionList: nutritionList, compositionList: compositionList])
    }


    def showImage(FoodLibrary foodLibrary) {
        if ( foodLibrary == null) {
            flash.message = "Food library not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(foodLibrary.id, 'food_library', response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "food library not found."
                redirect (action:'index')
            }
        }
    }

    def showElementImage(Integer id) {
        if ( id == null) {
            flash.message = "Food library not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(id, 'food_library', response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "food library not found."
                redirect (action:'index')
            }
        }
    }
}
