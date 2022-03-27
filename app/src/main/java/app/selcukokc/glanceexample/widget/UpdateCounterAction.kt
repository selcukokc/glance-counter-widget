package app.selcukokc.glanceexample.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import app.selcukokc.glanceexample.widget.CounterWidget.Companion.actionKey
import app.selcukokc.glanceexample.widget.CounterWidget.Companion.preferencesKey

class UpdateCounterAction : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val count = requireNotNull(parameters[actionKey]).let {
            if(it < 0){
                return@let 0
            } else {
                return@let it
            }
        }
        

        updateAppWidgetState(
            context = context,
            definition = PreferencesGlanceStateDefinition,
            glanceId = glanceId
        ) { preferences ->
            preferences.toMutablePreferences()
                .apply {
                    this[preferencesKey] = count
                }
        }

        CounterWidget().update(context, glanceId)
    }
}




