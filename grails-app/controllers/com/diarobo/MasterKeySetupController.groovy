package com.diarobo

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(CommonUtils.ROLE_ADMIN)
class MasterKeySetupController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MasterKeySetup.list(params), model:[masterKeySetupCount: MasterKeySetup.count()]
    }

    def show(MasterKeySetup masterKeySetup) {
        respond masterKeySetup
    }

    def create() {
        respond new MasterKeySetup(params)
    }

    @Transactional
    def save(MasterKeySetup masterKeySetup) {
        if (masterKeySetup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (masterKeySetup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond masterKeySetup.errors, view:'create'
            return
        }

        masterKeySetup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'masterKeySetup.label', default: 'MasterKeySetup'), masterKeySetup.id])
                redirect masterKeySetup
            }
            '*' { respond masterKeySetup, [status: CREATED] }
        }
    }

    def edit(MasterKeySetup masterKeySetup) {
        respond masterKeySetup
    }

    @Transactional
    def update(MasterKeySetup masterKeySetup) {
        if (masterKeySetup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (masterKeySetup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond masterKeySetup.errors, view:'edit'
            return
        }

        masterKeySetup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'masterKeySetup.label', default: 'MasterKeySetup'), masterKeySetup.id])
                redirect masterKeySetup
            }
            '*'{ respond masterKeySetup, [status: OK] }
        }
    }

    @Transactional
    def delete(MasterKeySetup masterKeySetup) {

        if (masterKeySetup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        masterKeySetup.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'masterKeySetup.label', default: 'MasterKeySetup'), masterKeySetup.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'masterKeySetup.label', default: 'MasterKeySetup'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
