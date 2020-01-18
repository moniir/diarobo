package diarobo

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/api/v1/$action?/$id?(.${format})?"(controller: 'diaroboApi')
        "/"(controller: 'dashboard')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
