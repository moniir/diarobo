package security

import com.diarobo.CellNumberVerification
import com.diarobo.CommonUtils
import com.diarobo.PatientProfile
import com.diarobo.Role
import com.diarobo.User
import com.diarobo.UserRole
import com.diarobo.commands.CellNumberVerificationCommand
import com.diarobo.commands.UserCommand
import com.diarobo.enums.SmsStatus
import com.diarobo.enums.VerificationStatus
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils
import org.springframework.http.HttpRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder

import javax.servlet.http.HttpServletRequest

@Transactional
class LoginService {

    def smsService
    def commonService
    AuthenticationManager authenticationManager

    def forgetPassword(String username, HttpServletRequest request) {
        User user = User.findByUsername(username)
        if(user) {
            if(sendCellVerificationCode(user, request)) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }


    def phoneVerification(CellNumberVerificationCommand command) {
        HashMap output
        User user = User.findByUsername(command.username)
        if(user) {
            CellNumberVerification cellNumberVerification = CellNumberVerification.findByUserAndVerificationCode(user, command.verificationCode)
            if(cellNumberVerification) {
                CellNumberVerification checkedExpiration = CellNumberVerification.findByUserAndExpiredDateGreaterThanEquals(user, new Date())
                if(checkedExpiration) {
                    String passwordGenerated = generateVerificationCode(8)
                    String smsMessage = 'DIAROBO Registration: You can login using: User id: ' + user.username +' and Password: ' + passwordGenerated
                    int status = smsService.sendSMS(user.username, smsMessage)
                    if(status == SmsStatus.SUCCESS.value) {
                        user.password = passwordGenerated
                        user.save(flush: true)
                        verifyPhone(user, cellNumberVerification)
                        userLoginInternally(user.username, passwordGenerated)
                        output = [hasError: false, command: command, userType: user.userType]
                        return output
                    } else {
                        output = [hasError: true, command: command, userType: '']
                        command.errors.reject('phone.number.invalid')
                        return output
                    }
                } else {
                    command.errors.reject('phone.verificationCode.Expired')
                    output = [hasError: true, command: command, userType: '']
                    command.errors.reject('phone.number.invalid')
                    return output
                }
            } else {
                command.errors.reject('phone.verificationCode.notMatch')
                output = [hasError: true, command: command, userType: '']
                command.errors.reject('phone.number.invalid')
                return output
            }
        } else {
            command.errors.reject('phone.doesNot.exist')
            output = [hasError: true, command: command, userType: '']
            command.errors.reject('phone.number.invalid')
            return output
        }
    }


    def registration(UserCommand command, HttpServletRequest request) {
        HashMap output
        //check if phone number already exist
        int count = User.countByUsername(command.username)
        if (count > 0) {
            command.errors.reject('phone.already.exist')
            output = [hasError: true, command: command]
            println(output)
            return output
        }
        String verificationCode = generateVerificationCode()
        String smsMessage = 'Welcome to DIAROBO. Your phone verification code is: ' + verificationCode
        int status = smsService.sendSMS(command.username, smsMessage)
        if(status == SmsStatus.SUCCESS.value) {
            User user = new User(username: command.username, password: tampPassword(), nickName: command.nickName,
                    fullName: command.fullName, userType: command.userType, enabled: false).save(flush: true)
            if(user) {
                def role = Role.findByAuthority(getUserRoleType(user))
                UserRole userRole = UserRole.findByUser(user) ?: new UserRole(user: user, role: role).save(flush: true)

                CellNumberVerification cellNumberVerification = new CellNumberVerification(user: user, verificationCode:
                        verificationCode, requestedFrom: getClientIp(request), status: VerificationStatus.PREPARED.value, expiredDate: getExpiredDateTime()
                ).save(flush: true)
                output = [hasError: false, command: command]
                return output
            } else {
                command.errors.reject('server.error')
                output = [hasError: true, command: command]
                println(output)
                return output
            }
        } else if(status == SmsStatus.INVALID.value){
            command.errors.reject('phone.number.invalid')
            output = [hasError: true, command: command]
            println(output)
            return output
        } else {
            command.errors.reject('server.error')
            output = [hasError: true, command: command]
            println(output)
            return output
        }
    }


    private String tampPassword(){
        return "123"
    }

    public Collection<GrantedAuthority> getAuthorities(User user) {
        //make everyone ROLE_USER
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>()
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            //anonymous inner type
            public String getAuthority() {
                return getUserRoleType(user)
            }
        };
        grantedAuthorities.add(grantedAuthority)
        return grantedAuthorities
    }

    def sendCellVerificationCode(User user, HttpServletRequest request) {
        CellNumberVerification cellNumberVerification = CellNumberVerification.findByUser(user)
        String verificationCode = generateVerificationCode()
        if(cellNumberVerification) {
            cellNumberVerification.status = VerificationStatus.PREPARED.value
            cellNumberVerification.expiredDate = getExpiredDateTime()
            cellNumberVerification.verificationCode = verificationCode
            cellNumberVerification.requestedFrom = getClientIp(request)
            cellNumberVerification.save(flush: true)
        } else {
            cellNumberVerification = new CellNumberVerification(user: user, verificationCode:
                    verificationCode, requestedFrom: getClientIp(request), status: VerificationStatus.PREPARED.value, expiredDate: getExpiredDateTime()
            ).save(flush: true)
        }

        if(cellNumberVerification) {
            String smsMessage = 'Welcome to DIAROBO. Your phone verification code is: ' + verificationCode
            println(smsMessage)
            int status = smsService.sendSMS(user.username, smsMessage)
            if(status == SmsStatus.SUCCESS.value) {
                cellNumberVerification.status = VerificationStatus.SENT.value
            }
            return status
        } else {
            return SmsStatus.INVALID.value
        }
    }

    def verifyPhone(User user, CellNumberVerification cellNumberVerification) {
        user.enabled = true
        user.save(flush: true)
        cellNumberVerification.status = VerificationStatus.VERIFIED.value
        cellNumberVerification.save(flush: true)
        new PatientProfile(user: user).save(flush:true)
    }

    def getUserRoleType(User user) {
        switch (user.userType) {
            case 'Patient': return CommonUtils.ROLE_PATIENT
                break
            case 'Caregiver': return CommonUtils.ROLE_CAREGIVER
                break
            case 'Doctor': return CommonUtils.ROLE_DOCTOR
                break
        }
    }

    def getExpiredDateTime() {
        Calendar cal = Calendar.getInstance()
        cal.setTime(new Date())
        cal.add(Calendar.MINUTE, 5) //added 5 minutes
        Date expiredDate = cal.getTime()
        return expiredDate
    }

    def generateVerificationCode(int length = 4){
        return RandomStringUtils.randomAlphabetic(length)
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String headerClientIp = request.getHeader("Client-IP");
        String headerXForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) && StringUtils.isNotEmpty(headerClientIp)) {
            ip = headerClientIp;
        } else if (StringUtils.isNotEmpty(headerXForwardedFor)) {
            ip = headerXForwardedFor;
        }
        return ip
    }

    def userLoginInternally(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password)
        User details = User.findByUsername(username)
        token.setDetails(details)

        try {
            Authentication auth = authenticationManager.authenticate(token)
            log.debug("Login succeeded!")
            SecurityContextHolder.getContext().setAuthentication(auth)
            return true
        } catch (BadCredentialsException e) {
            return false
        }
    }

    def userLoginInternallyWithoutPassword(String username) {
        User details = User.findByUsername(username)
        Authentication authentication = new UsernamePasswordAuthenticationToken(details, null,
                AuthorityUtils.createAuthorityList(getUserRoleType(details)))
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }
}
