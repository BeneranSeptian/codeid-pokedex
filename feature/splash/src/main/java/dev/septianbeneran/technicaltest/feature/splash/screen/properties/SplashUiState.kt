package dev.septianbeneran.technicaltest.feature.splash.screen.properties

import dev.septianbeneran.technicaltest.core.base.BaseEvent

sealed interface SplashEvent: BaseEvent {
    data object NavigateToHome : SplashEvent
    data object NavigateToAuth : SplashEvent
}