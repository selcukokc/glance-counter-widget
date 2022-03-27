package app.selcukokc.glanceexample.widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import app.selcukokc.glanceexample.R
import androidx.datastore.preferences.core.Preferences
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import app.selcukokc.glanceexample.MainActivity

class CounterWidget: GlanceAppWidget() {

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    companion object{
        val actionKey = ActionParameters.Key<Int>("actionKey")
        val preferencesKey = intPreferencesKey("preferencesKey")
    }

    private val SMALL_BOX = DpSize(80.dp, 40.dp)
    private val WIDE_BOX = DpSize(240.dp, 40.dp)
    private val LARGE_BOX = DpSize(200.dp, 200.dp)

    override val sizeMode: SizeMode
        get() = SizeMode.Responsive(
            setOf(SMALL_BOX, WIDE_BOX, LARGE_BOX)
        )

    @Composable
    override fun Content() {
        when(LocalSize.current){
            SMALL_BOX -> SmallWidget()
            WIDE_BOX -> WideWidget()
            LARGE_BOX -> LargeWidget()
        }
    }


    @Composable
    private fun SmallWidget(){
        val prefs = currentState<Preferences>()
        val counter = prefs[preferencesKey] ?: 0

        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_background))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ){

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_remove_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .clickable(actionRunCallback<UpdateCounterAction>(
                        parameters = actionParametersOf(actionKey to (counter-1))
                    ))
                    .defaultWeight()
                    .padding(top = 10.dp)
            )

            Text(
                text = counter.toString(),
                modifier = GlanceModifier.padding(horizontal = 12.dp),
                style = TextStyle(
                    color = ColorProvider(R.color.white),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_add_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .clickable(actionRunCallback<UpdateCounterAction>(
                        parameters = actionParametersOf(actionKey to (counter+1))
                    ))
                    .defaultWeight()
                    .padding(top = 10.dp)
            )

        }
    }


    @Composable
    private fun WideWidget(){
        val context = LocalContext.current
        val prefs = currentState<Preferences>()
        val counter = prefs[preferencesKey] ?: 0
        
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_background))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_input_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .defaultWeight()
                    .clickable(
                        actionStartActivity(Intent(context, MainActivity::class.java))
                    )
            )

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_remove_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .defaultWeight()
                    .clickable(actionRunCallback<UpdateCounterAction>(
                        parameters = actionParametersOf(actionKey to (counter-1))
                    ))
            )

            Text(
                text = counter.toString(),
                modifier = GlanceModifier.padding(horizontal = 12.dp),
                style = TextStyle(
                    color = ColorProvider(R.color.white),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_add_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .defaultWeight()
                    .clickable(actionRunCallback<UpdateCounterAction>(
                        parameters = actionParametersOf(actionKey to (counter+1))
                    ))
            )

            Image(
                provider = ImageProvider(R.drawable.ic_baseline_refresh_24),
                contentDescription = null,
                modifier = GlanceModifier
                    .defaultWeight()
                    .clickable(actionRunCallback<UpdateCounterAction>(
                        parameters = actionParametersOf(actionKey to 0)
                    ))
            )

        }
    }


    @Composable
    private fun LargeWidget(){
        val context = LocalContext.current
        val prefs = currentState<Preferences>()
        val counter = prefs[preferencesKey] ?: 0

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_background))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_baseline_input_24),
                    contentDescription = null,
                    modifier = GlanceModifier
                        .defaultWeight()
                        .clickable(
                            actionStartActivity(Intent(context, MainActivity::class.java))
                        )
                )

                Image(
                    provider = ImageProvider(R.drawable.ic_baseline_refresh_24),
                    contentDescription = null,
                    modifier = GlanceModifier
                        .defaultWeight()
                        .clickable(
                            actionRunCallback<UpdateCounterAction>(parameters = actionParametersOf(actionKey to 0))
                        )
                )
            }

            Text(
                text = counter.toString(),
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .defaultWeight(),
                style = TextStyle(
                    color = ColorProvider(R.color.white),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    provider = ImageProvider(R.drawable.ic_baseline_remove_24),
                    contentDescription = null,
                    modifier = GlanceModifier
                        .clickable(actionRunCallback<UpdateCounterAction>(
                            parameters = actionParametersOf(actionKey to (counter-1))
                        ))
                        .defaultWeight()
                )

                Image(
                    provider = ImageProvider(R.drawable.ic_baseline_add_24),
                    contentDescription = null,
                    modifier = GlanceModifier
                        .clickable(actionRunCallback<UpdateCounterAction>(
                            parameters = actionParametersOf(actionKey to (counter+1))
                        ))
                        .defaultWeight()
                )
            }

        }
    }


}