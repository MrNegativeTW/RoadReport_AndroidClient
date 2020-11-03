package com.txwstudio.app.roadreport.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.txwstudio.app.roadreport.R
import com.txwstudio.app.roadreport.Util
import com.txwstudio.app.roadreport.databinding.RowEventCardBinding
import com.txwstudio.app.roadreport.firebase.AuthManager
import com.txwstudio.app.roadreport.firebase.FirestoreManager
import com.txwstudio.app.roadreport.model.Accident
import com.txwstudio.app.roadreport.model.EventCardVewModel
import com.txwstudio.app.roadreport.ui.maps.MapsFragment

class EventCardAdapter(
    private val requireView: View,
    private val fm: FragmentManager,
    val roadCode: Int
) :
    FirestoreRecyclerAdapter<Accident?, EventCardAdapter.ViewHolder>(
        FirestoreManager().getRealtimeAccidentQuery(roadCode)
    ) {

    private var isUserSignedIn = AuthManager().isUserSignedIn()
    private val currentUserUid = AuthManager().getCurrUserModel()?.uid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowEventCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Accident) {
        holder.bind(model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: RowEventCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.textViewAccidentCardLocation.setOnClickListener {
                binding.eventCardViewModel?.locationGeoPoint?.let { LatLng ->
                    MapsFragment(false, LatLng).show(fm, MapsFragment::class.java.simpleName)
                }
            }

            binding.imageButtonAccidentCardMore.setOnClickListener {
                // TODO(Fix event card pattern to match MVVM)
                // What user can to the card. Different onClick behavior for card.
                // Situation 1: NOT Signed in, no action at all.
                // Situation 2: Signed in && posted by user, Edit or Delete.
                // Situation 3: Signed in && NOT posted by user, Report.
                if (!isUserSignedIn) {
                    // Situation 1
                    binding.imageButtonAccidentCardMore.visibility = View.INVISIBLE

                } else if (isUserSignedIn && snapshots.getSnapshot(adapterPosition)["userUid"] == currentUserUid) {
                    // Situation 2, 0 for edit, 1 for delete.
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setItems(R.array.roadFrag_moreOnClick_situation2) { _, which ->
                        when (which) {
                            0 -> {
                                Log.i("TESTTT", "編輯")
                            }
                            1 -> {
                                Log.i("TESTTT", "刪除")
                                FirestoreManager()
                                    .deleteAccident(
                                        roadCode,
                                        snapshots.getSnapshot(adapterPosition).id
                                    ) {
                                        Util().snackBarShort(
                                            requireView,
                                            if (it) "刪除成功" else "刪除失敗"
                                        )
                                    }
                            }
                        }
                    }.show()

                } else if (isUserSignedIn && snapshots.getSnapshot(adapterPosition)["userUid"] != currentUserUid) {
                    // Situation 3
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setItems(R.array.roadFrag_moreOnClick_situation3) { _, which ->
                        when (which) {
                            0 -> {
                                Util().snackBarShort(requireView, "欸嘿 還沒開發")
                            }
                        }
                    }.show()

                }
            }
        }


        fun bind(events: Accident) {
            binding.apply {
                eventCardViewModel = EventCardVewModel(events)
                executePendingBindings()
            }
        }

    }
}
