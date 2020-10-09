package com.example.criminalinten

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.time.format.DateTimeFormatter

private const val TAG = "CrimeListFragment"
private lateinit var crimeRecyclerView: RecyclerView



class CrimeListFragment : Fragment() {
    private var adapter: CrimeAdapter? = null
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes:${crimeListViewModel.crimes.size}")
    }
    companion object {

        const val serious_Crime=1
        const val normal_Crime=0
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list,
            container, false)
        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as
                    RecyclerView
        crimeRecyclerView.layoutManager =
            LinearLayoutManager(context)
        updateUI()

        return view
    }
    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }



    private inner class CrimeAdapter(var crimes: List<Crime>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private inner class CrimeHolder(view: View)
            : RecyclerView.ViewHolder(view) , View.OnClickListener{
            private lateinit var crime: Crime
            init {
                itemView.setOnClickListener(this)
            }

            private val titleTextView: TextView =
                itemView.findViewById(R.id.crime_title)
            private val dateTextView: TextView =
                itemView.findViewById(R.id.crime_date)
            private  val imageSolved:ImageView=itemView.findViewById(R.id.crime_solve)


            fun bind(crime: Crime) {
                this.crime = crime
                titleTextView.text = this.crime.title
                dateTextView.text = DateFormat.getDateInstance(DateFormat.FULL).format(this.crime.date).toString()
                imageSolved.visibility=if(crime.isSolved){View.VISIBLE} else{View.GONE}

            }

            override fun onClick(v: View?) {
                Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()

            }

        }
        private inner class SeriousCrimeHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
            private lateinit var crime: Crime
            init {
                itemView.setOnClickListener(this)
            }

            private val titleTextView: TextView =
                itemView.findViewById(R.id.crime_title)
            private val dateTextView: TextView =
                itemView.findViewById(R.id.crime_date)
            private val callPoliceButton:Button=itemView.findViewById(R.id.Serious)
            private  val imageSolved:ImageView=itemView.findViewById(R.id.crime_Image)

            fun bind(crime: Crime) {
                this.crime = crime
                titleTextView.text = this.crime.title
                dateTextView.text = DateFormat.getDateInstance(DateFormat.FULL).format(this.crime.date).toString()
                callPoliceButton.text="Contact Police"
                imageSolved.visibility=if(crime.isSolved){View.VISIBLE} else{View.GONE}

            }

            override fun onClick(v: View?) {
                Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()

            }



        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int)
                : RecyclerView.ViewHolder {

            val view :RecyclerView.ViewHolder=when(viewType)
            {
                normal_Crime->CrimeHolder(layoutInflater.inflate(R.layout.list_item_crime, parent, false))
                else->SeriousCrimeHolder(layoutInflater.inflate(R.layout.serious_crime,parent,false))

            }

            return view
        }

        override fun getItemViewType(position: Int): Int {
            val type=when(crimes[position].requirePolice){
                 true-> serious_Crime
                else -> normal_Crime
                  }
            return type;
        }
        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val type=when(crimes[position].requirePolice){
                true-> (holder as SeriousCrimeHolder).bind(crimes[position])
                else -> (holder as CrimeHolder).bind(crimes[position])
            }



        }
    }


}