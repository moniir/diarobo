package com.diarobo

class BreadCrumbActionsTagLib {
    static namespace = "diarobo"

    def breadCrumbs = { attrs, body ->
        def bread1Text = attrs.bread1Text ?: 'BreadCrumb 1'
        def bread1Url = attrs.bread1Url
        def bread2Text = attrs.bread2Text ?: 'BreadCrumb 2'
        def bread2Url = attrs.bread2Url
        def bread3Text = attrs.bread3Text ?: 'BreadCrumb 3'
        def bread3Url = attrs.bread3Url
        def breadTitle = attrs.breadTitle ?: "Page Title"
        out << render(template: "/layouts/breadCrumb", model: [bread1Text : bread1Text, bread1Url: bread1Url, bread2Text: bread2Text, bread2Url: bread2Url, bread3Text : bread3Text, bread3Url: bread3Url, breadTitle : breadTitle])
    }
}
