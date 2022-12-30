package com.mudassar.mykitten.di

import com.mudassar.mykitten.model.AppConfig
import com.mudassar.mykitten.model.Platform

import org.koin.dsl.module
import org.koin.ksp.generated.module

fun initDiGraph(config: AppConfig, platform: Platform) =
    configureKoin {
        modules(MobileModule().module,
            module {
                factory { config }
                factory { platform }
                single { platform.eventLogger }
            }
        )
    }

