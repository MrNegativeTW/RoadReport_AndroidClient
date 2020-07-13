package com.txwstudio.app.roadreport.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.road_accident_row.view.*

class AccidentCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var situationType = itemView.textView_accidentCard_situationType as TextView
    var location = itemView.textView_accidentCard_location as TextView
    var moreButton = itemView.imageButton_accidentCard_more as ImageButton
    var situation = itemView.textView_accidentCard_situation as TextView
    var image = itemView.imageView_accidentCard_image as ImageView
    var time = itemView.textView_accidentCard_time as TextView
    var userName = itemView.textView_accidentCard_userName as TextView
    var layout = itemView.relaLayout_accidentCard as RelativeLayout
}