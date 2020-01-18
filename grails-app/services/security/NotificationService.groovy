package security
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NotificationService {

    // Method to send Notifications from server to client end.

    public final static String AUTH_KEY_FCM = "AIzaSyDMHsxDk5D7g1Y0j1X6smbaXzXuF_4WIXk"
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

    def pushFCMNotification(String userDeviceIdKey, String body) throws Exception{

        String authKey = AUTH_KEY_FCM
        String FMCurl = API_URL_FCM

        URL url = new URL(FMCurl)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setUseCaches(false)
        conn.setDoInput(true)
        conn.setDoOutput(true)

        conn.setRequestMethod("POST")
        conn.setRequestProperty("Authorization","key=" + authKey)
        conn.setRequestProperty("Content-Type","application/json")

        JSONObject json = new JSONObject()
        json.put("to",userDeviceIdKey.trim())
        JSONObject info = new JSONObject()
        info.put("title", "Notificatoin Title")
        info.put("body", body)
        json.put("notification", info)

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())
        wr.write(json.toString())
        wr.flush()
        conn.getInputStream()
    }
}
