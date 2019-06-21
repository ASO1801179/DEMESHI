package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_my_card_list.*

class MyCardList : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card_list)
        MyCardBtn.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        HaveCardBtn.setOnClickListener{startActivity(Intent(it.context,HaveCardList::class.java))}
        PhotoCardBtn.setOnClickListener{startActivity(Intent(it.context,PhotoCard::class.java))}
        AddInd.setOnClickListener{startActivity(Intent(it.context,Template::class.java))}
        MyCard.setOnClickListener{startActivity(Intent(it.context,ShowMyCard::class.java))}
        Name.setOnClickListener{startActivity(Intent(it.context,ShowMyCard::class.java))}
        CompanyName.setOnClickListener{startActivity(Intent(it.context,ShowMyCard::class.java))}
    }
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.setting,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.UserConfig ->{
                val intent = Intent(this,UserConfig::class.java)
                startActivity(intent)
            }
            R.id.Delete->{
                val intent = Intent(this,Login::class.java)
                AlertDialog.Builder(this).apply {
                    setTitle("退会")
                    setMessage("本当に退会しますか？")
                    setPositiveButton("退会", DialogInterface.OnClickListener { _, _ ->
                        // OKをタップしたときの処理
                        Toast.makeText(context, "退会しました", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    })
                    setNegativeButton("キャンセル", null)
                    show()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
