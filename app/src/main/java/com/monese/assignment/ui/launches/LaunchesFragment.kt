package com.monese.assignment.ui.launches

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monese.assignment.R
import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.repository.LaunchesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchesFragment()
    }

    @Inject
    lateinit var dateFormatter: DateFormatter

    @Inject
    lateinit var launchesRepository: LaunchesRepository

    private val viewModel by viewModels<LaunchesViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var sw: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.launches_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.rw_launches).apply {
            setHasFixedSize(true)
        }

        sw = view.findViewById(R.id.swipe_to_refresh)

        sw.setOnRefreshListener {
            viewModel.scope.launch {
                launchesRepository.getLaunches(true).let { result ->
                    if (result is Response.Success) {
                        sw.isRefreshing = false
                        (recyclerView.adapter as LaunchesAdapter).setList(result.data)
                    }
                }
            }
        }

        viewModel.getLaunches()
        viewModel.launches.observe(
            this.viewLifecycleOwner, {
                Timber.d(it.toString())
                recyclerView.adapter = LaunchesAdapter(
                    it,
                    dateFormatter
                )
            })
    }
}

private class LaunchesAdapter(
    launches: List<Launch>,
    private val dateFormatter: DateFormatter
) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    private var l = launches

    fun setList(list: List<Launch>) {
        l = list
        notifyDataSetChanged()
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.launch_row_item, parent, false)) {

        private var tvName: TextView? = null
        private var tvDate: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvDate = itemView.findViewById(R.id.tv_date)
        }

        fun bind(launch: Launch, dateFormatter: DateFormatter) {
            tvName!!.text = launch.name
            tvDate!!.text = dateFormatter.formatDate(launch.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return l.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = l[position]
        holder.bind(launch, dateFormatter)
    }
}

class DateFormatter @Inject constructor() {

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("d MMM yyyy HH:mm:ss")

    fun formatDate(unix: Long): String {
        return formatter.format(Date(unix * 1000))
    }
}