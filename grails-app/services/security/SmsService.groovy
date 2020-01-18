package security

import com.diarobo.enums.SmsStatus
import grails.converters.JSON
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

class SmsService {
    static transactional = false

    def commonService
    def grailsApplication

    def sendSMS(String cellNumber, String message) throws Exception{
        StringBuilder stringBuilder = new StringBuilder("https://powersms.banglaphone.net.bd/httpapi/sendsms?userId=grailslab&password=grailslab123")
        message = commonService.convertForUrlParam(message)
        stringBuilder.append("&smsText=" + message)
        stringBuilder.append("&commaSeperatedReceiverNumbers=" + cellNumber)
        final String USER_AGENT = "Mozilla/5.0";
        if(grailsApplication.config.smsSendEnabled) {
            try{

                URL obj = new URL(stringBuilder.toString());
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    println(inputLine)
                    response.append(inputLine);
                }
                JSONObject responseJson = JSON.parse(response.toString())
                println(responseJson.get('message'))
                String responseMessage = responseJson.get('message')
                println(responseMessage)
                if(responseMessage.contains('Success!')) {
                    return SmsStatus.SUCCESS.value
                } else if (responseMessage.contains('invalid')) {
                    return SmsStatus.INVALID.value
                } else {
                    return SmsStatus.ERROR.value
                }
                bufferedReader.close();
            } catch (Exception e) {
                log.error(e.getMessage())
                return SmsStatus.ERROR.value
            }
        } else {
            println(message)
            return SmsStatus.SUCCESS.value
        }
    }
}
