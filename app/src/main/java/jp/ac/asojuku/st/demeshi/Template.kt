package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_template.*

class Template : AppCompatActivity() {

    val user_id = intent.getIntExtra("UserId",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        Back.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        Template1.setOnClickListener{Create(1)}
        Template1.setOnClickListener{Create(2)}
        Template1.setOnClickListener{Create(3)}
        Template1.setOnClickListener{Create(4)}
        Template1.setOnClickListener{Create(5)}
        Template1.setOnClickListener{Create(6)}
        Template1.setOnClickListener{Create(7)}
        Template1.setOnClickListener{Create(8)}
        Template1.setOnClickListener{Create(9)}
    }
    fun Create(num:Int){
        when((num-1)/3){
            0->{
                //CreateCard
                val intent = Intent(this,CreateCard::class.java)
                intent.putExtra("Image",num)
                intent.putExtra("UserId",user_id)
                startActivity(intent)
            }
            1->{
                //CreateIndividual
                val intent = Intent(this,CompanyInfo::class.java)
                intent.putExtra("Image",num)
                intent.putExtra("Confirm","Individual")
                intent.putExtra("UserId",user_id)
            }
            2->{
                //CreateCompany
                val intent = Intent(this,CompanyInfo::class.java)
                intent.putExtra("Image",num)
                intent.putExtra("Confirm","Company")
                intent.putExtra("UserId",user_id)
            }
        }
    }
}