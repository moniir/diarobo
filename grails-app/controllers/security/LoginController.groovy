package security

import com.diarobo.*
import com.diarobo.commands.CellNumberVerificationCommand
import com.diarobo.commands.ResetPasswordCommand
import com.diarobo.commands.UserCommand
import com.diarobo.enums.MasterKeyValue
import com.diarobo.enums.SmsStatus
import com.diarobo.enums.VerificationStatus
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import org.grails.web.json.JSONObject
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes

@Secured('permitAll')
class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService
    def masterKeyService
    def loginService
    def smsService
    def commonService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    @Secured('permitAll')
    def auth() {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                                   rememberMeParameter: config.rememberMe.parameter]
    }

    @Secured('permitAll')
    def facebookGoogleRegistration() {
        def userTypes = masterKeyService.keyDropDownList(MasterKeyValue.USER_TYPE.value)
        def responseData
        UserAuthIdentity userAuthIdentity = UserAuthIdentity.findByUserAccountId(params.userAccountId)
        responseData = ['hasError': true, 'message': 'Something error. Please try again']
        if(!userAuthIdentity) {
            userAuthIdentity = new UserAuthIdentity(userAccountId: params.userAccountId, providerName: params.providerName, fullName: params.fullName, nickName: params.nickName)
            def userAuthIdentitySaved = userAuthIdentity.save(flush: true)
            if(userAuthIdentitySaved) {
                render(view: 'registrationMoreInfo', model: [userTypes: userTypes, userAccountId: params.userAccountId])
                return
            } else {
                responseData = ['hasError': true, 'message': 'Something error. Please try again']
            }
        } else {
            if(!userAuthIdentity.username) {
                render(view: 'registrationMoreInfo', model: [userTypes: userTypes, userAccountId: params.userAccountId])
                return
            } else {
                responseData = ['hasError': true, 'message': 'Already Registered. You can login now.']
            }
        }
        render responseData as JSON
    }

    @Secured('permitAll')
    def registrationProviderMoreInfo() {
        def userTypes = masterKeyService.keyDropDownList(MasterKeyValue.USER_TYPE.value)
        def responseData
        UserAuthIdentity userAuthIdentity = UserAuthIdentity.findByUserAccountId(params.userAccountId)
        UserCommand command = new UserCommand()
        command.username = params.username
        command.userType = params.userType
        if(userAuthIdentity) {
            command.nickName = userAuthIdentity.nickName
            command.fullName = userAuthIdentity.fullName
            def result
            result = loginService.registration(command, request)
            if (result.hasError) {
                render(view: 'registration', model: [userCommand: command, userTypes: userTypes])
                return
            } else {
                userAuthIdentity.username = params.username
                userAuthIdentity.save(flush: true)
                redirect(action: 'phoneVerification', params: [username: command.username])
                return
            }
        } else {
            render(view: 'registration', model: [userCommand: command, userTypes: userTypes])
        }
    }


    @Secured('permitAll')
    def facebookGoogleLogin() {
        def responseData
        UserAuthIdentity userAuthIdentity = UserAuthIdentity.findByUserAccountIdAndProviderName(params.userAccountId, params.providerName)
        User user = User.findByUsernameAndEnabled(userAuthIdentity.username, true)
        if(user) {
            def config = SpringSecurityUtils.securityConfig
            loginService.userLoginInternallyWithoutPassword(user.username)
            redirect uri: config.successHandler.defaultTargetUrl
            return
        } else {
            flash.message = "User Not found. You have to register first & try again."
            redirect action: 'auth'
        }
    }

    @Secured('permitAll')
    def reSendVerificationCode() {
        User user = User.findByUsername(params.username)
        if(user) {
            if(loginService.sendCellVerificationCode(user , request) == SmsStatus.SUCCESS.value) {
                render(status: 200, text: 'OK')
                return
            } else {
                render(status: 500, text: 'ERROR')
                return
            }
        } else {
            render(status: 500, text: 'ERROR')
        }
    }

    @Secured('permitAll')
    def registration(UserCommand command) {
        def userTypes = masterKeyService.keyDropDownList(MasterKeyValue.USER_TYPE.value)
        switch (request.method) {
            case 'GET':
                render(view: 'registration', model: [userTypes: userTypes])
                return
                break
            case 'POST':
                def result
                if (command.hasErrors()) {
                    render(view: 'registration', model: [userCommand: command, userTypes: userTypes])
                    return
                }
                result = loginService.registration(command, request)
                if (result.hasError) {
                    render(view: 'registration', model: [userCommand: result.command, userTypes: userTypes])
                    return
                } else {
                    redirect(action: 'phoneVerification', params: [username: result.command.username])
                }
                break
        }
    }





    @Secured('permitAll')
    def phoneVerification(CellNumberVerificationCommand command) {
        switch (request.method) {
            case 'GET':
                render(view: 'phoneVerification', model: [cellNumberVerificationCommand: command])
                return
                break
            case 'POST':
                if (command.hasErrors()) {
                    render(view: 'phoneVerification', model: [cellNumberVerificationCommand: command])
                    return
                }
                def result =loginService.phoneVerification(command)
                if (result.hasError) {
                    render(view: 'phoneVerification', model: [cellNumberVerificationCommand: command])
                    return
                } else {
                    render(view: 'phoneConfirmation', model: [userType: result.userType, username: command.username])
                }
                break
        }
    }
    @Secured('permitAll')
    def forgetPassword(String username) {
        switch (request.method) {
            case 'GET':
                render(view: 'forgetPassword', model: [username: username])
                return
                break
            case 'POST':
                boolean hasError = loginService.forgetPassword(username,request)
                if(hasError) {
                    flash.message = message(code: 'phone.doesNot.exist')
                    render(view: 'forgetPassword', model: [username: username])
                    return
                } else {
                    render(view: 'resetPassword', model: [username: username])
                    return
                }
                break
        }
    }
    @Secured('permitAll')
    def resetPassword(ResetPasswordCommand command) {
        switch (request.method) {
            case 'GET':
                render(view: 'resetPassword', model: [resetPasswordCommand: command])
                return
                break
            case 'POST':
                if (command.hasErrors()) {
                    render(view: 'resetPassword', model: [resetPasswordCommand: command])
                    return
                }
                User user = User.findByUsername(command.username)
                if(user) {
                    CellNumberVerification cellNumberVerification = CellNumberVerification.findByUserAndVerificationCode(user, command.verificationCode)
                    if(cellNumberVerification) {
                        CellNumberVerification checkedExpiration = CellNumberVerification.findByUserAndExpiredDateGreaterThanEquals(user, new Date())
                        if(checkedExpiration) {
                            loginService.verifyPhone(user, cellNumberVerification)
                            user.password = command.newPassword
                            user.save(flush: true)
                            render(view: 'resetPasswordConfirmation')
                            return
                        } else {
                            command.errors.reject('phone.verificationCode.Expired')
                            render(view: 'resetPassword', model: [resetPasswordCommand: command])
                            return
                        }
                    } else {
                        command.errors.reject('phone.verificationCode.notMatch')
                        render(view: 'resetPassword', model: [resetPasswordCommand: command])
                        return
                    }
                } else {
                    command.errors.reject('phone.doesNot.exist')
                    render(view: 'resetPassword', model: [resetPasswordCommand: command])
                    }
                break
        }

    }


    /**
     * The redirect action for Ajax requests.
     */
    def authAjax() {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied() {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full() {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail() {

        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess() {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied() {
        render([error: 'access denied'] as JSON)
    }


}