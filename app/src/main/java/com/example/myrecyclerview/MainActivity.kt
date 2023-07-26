package com.example.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myrecyclerview.numbersAdapter.numbersViewHolder

class MainActivity : AppCompatActivity() {
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.rcv) }
    val Results by lazy { findViewById<TextView>(R.id.tvNew) }
    var selectedItem : Int=-1
    private val onCickAction = fun (weekday: Any){
        if(selectedItem == weekday){
            return
        }
        val indexInit = numbers.indexOfFirst { it.dayOfMonth == selectedItem }
        if(indexInit!=-1){
            val item = numbers[indexInit]
            numbers[indexInit] = item.copy(selected = false)
            recyclerView.adapter?.notifyItemChanged(indexInit)
        }
        val index = numbers.indexOfFirst { it.dayOfMonth == weekday }
        if(index!=-1){
            val item = numbers[index]
            numbers[index] = item.copy(selected = true)
            selectedItem= numbers[index].dayOfMonth
            onChangeUpdateText(weekday)
            recyclerView.adapter?.notifyItemChanged(index)
        }

    }

    fun onChangeUpdateText(dayOfMonth : Any){
        Results.text=dayOfMonth.toString()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager=LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL,
            false
            )
        recyclerView.adapter= numbersAdapter(numbers,onCickAction )



    }
}


class numbersAdapter(private val list: List<WeekDay>,
        val onClickAction : (Any) ->Unit
                     ) : Adapter<numbersViewHolder>() {
    inner class numbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): numbersViewHolder {
        return numbersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: numbersViewHolder, position: Int) {
        val d= list[position]
        holder.itemView.findViewById<TextView>(R.id.tv1).text =
            d.weekDay
        holder.itemView.findViewById<TextView>(R.id.tv2).text =
            d.dayOfMonth.toString()
        holder.itemView.setOnClickListener{
            onClickAction (d.dayOfMonth)
            if(d.selected){
                holder.itemView.background = ResourcesCompat.getDrawable(
                    holder.itemView.context.resources,
                    R.drawable.week_day_selected_bg,
                    null
                )

            }
            else{
                holder.itemView.background = ResourcesCompat.getDrawable(
                    holder.itemView.context.resources,
                    R.drawable.week_day_bg,
                    null
                )
            }
        }

    }

}

data class WeekDay(
    val weekDay : String,
    val dayOfMonth: Int,
    val selected : Boolean=false
)

val numbers = mutableListOf<WeekDay>(
    WeekDay("Monday",1),
    WeekDay("Tuesday",2),
    WeekDay("Wednesday",3),
    WeekDay("Thursday",4),
    WeekDay("Friday",5),
    WeekDay("Saturday",6),
    WeekDay("Sunday",7)
)