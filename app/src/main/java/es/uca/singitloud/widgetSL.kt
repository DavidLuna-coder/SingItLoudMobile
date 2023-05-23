package es.uca.singitloud

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.annotation.CallSuper


/**
 * Implementation of App Widget functionality.
 */
class widgetSL : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_s_l)
            views.setTextViewText(R.id.textView, "Widget pulsado")
            // Agregamos un onClick en el TextView para abrir la MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.textView, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    @CallSuper
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == "OPEN_MAIN_ACTIVITY") {
            val mainActivityIntent = Intent(context, MainActivity::class.java)
            mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(mainActivityIntent)
        }
    }
}