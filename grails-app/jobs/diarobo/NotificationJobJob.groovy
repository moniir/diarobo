package diarobo

class NotificationJobJob {
    def prescriptionService
    static triggers = {
//        simple name: 'diaroboTrigger', startDelay: 20000, repeatInterval: 120000l // execute job repeat in 120 seconds & start after 2 second
      simple repeatInterval: 120000l, startDelay: 20000 // execute job once in 5 seconds
    }

    def execute() {
        // execute job
       // prescriptionService.modifyReminder('01821449288')
//        println "Job run!"
    }
}
