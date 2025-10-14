package dev.hotwire.demo.features.web

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import dev.hotwire.core.turbo.errors.HttpError
import dev.hotwire.core.turbo.errors.VisitError
import dev.hotwire.core.turbo.visit.VisitAction.REPLACE
import dev.hotwire.core.turbo.visit.VisitOptions
import dev.hotwire.core.turbo.webview.HotwireWebView
import dev.hotwire.demo.R
import dev.hotwire.navigation.destinations.HotwireDestinationDeepLink
import dev.hotwire.navigation.fragments.HotwireWebFragment
import dev.hotwire.navigation.session.SessionModalResult

@HotwireDestinationDeepLink(uri = "hotwire://fragment/web")
open class WebFragment : HotwireWebFragment() {
    private var startedAfterModalResult = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    override fun onWebViewDetached(webView: HotwireWebView) {
        if (!this.startedAfterModalResult) {
            // in the framework code there are extentions to get the value -> arguments?.location
            arguments?.putString("location", webView.url)
        }
        this.startedAfterModalResult = false
        super.onWebViewDetached(webView)
    }

    override fun onStartAfterModalResult(result: SessionModalResult) {
        this.startedAfterModalResult = true
        super.onStartAfterModalResult(result)
    }

    override fun onFormSubmissionStarted(location: String) {
        menuProgress?.isVisible = true
    }

    override fun onFormSubmissionFinished(location: String) {
        menuProgress?.isVisible = false
    }

    private fun setupMenu() {
        toolbarForNavigation()?.inflateMenu(R.menu.web)
    }

    private val menuProgress: MenuItem?
        get() = toolbarForNavigation()?.menu?.findItem(R.id.menu_progress)
}
