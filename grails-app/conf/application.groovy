environments {
	development {
		uploadFolder = "${System.properties['user.home']}/grailslab/diarobo/uploads"
		smsSendEnabled = false
	}
	test {
		uploadFolder = "${System.properties['user.home']}/grailslab/diarobo/uploads"
		smsSendEnabled = false
	}
	production {
		uploadFolder = "/usr/aminul/grailslab/diarobo/uploads"
		smsSendEnabled = true
	}
}

grails.controllers.upload.maxFileSize=10000000000000
grails.controllers.upload.maxRequestSize=10000000000000

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.diarobo.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.diarobo.UserRole'
grails.plugin.springsecurity.authority.className = 'com.diarobo.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/index',          access: ['permitAll']],
		[pattern: '/error',          access: ['permitAll']],
		[pattern: '/shutdown',       access: ['permitAll']],
		[pattern: '/assets/**',      access: ['permitAll']],
		[pattern: '/**/js/**',       access: ['permitAll']],
		[pattern: '/**/css/**',      access: ['permitAll']],
		[pattern: '/**/images/**',   access: ['permitAll']],
		[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/assets/**',      filters: 'none'],
		[pattern: '/**/js/**',       filters: 'none'],
		[pattern: '/**/css/**',      filters: 'none'],
		[pattern: '/**/images/**',   filters: 'none'],
		[pattern: '/**/favicon.ico', filters: 'none'],
		[pattern: '/**',             filters: 'JOINED_FILTERS']
]
grails.plugin.springsecurity.logout.postOnly = false
