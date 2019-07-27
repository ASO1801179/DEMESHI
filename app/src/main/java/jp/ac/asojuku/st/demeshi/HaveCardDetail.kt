package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_have_card_detail.*

class HaveCardDetail : AppCompatActivity() {

    var user_id = 0
    var card_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_detail)
        user_id = intent.getIntExtra("UserId",0)
        card_id = intent.getIntExtra("CardId",0)

        Back.setOnClickListener{
            val intent = Intent(this,HaveCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        Delete.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("名刺削除")
                setMessage("本当に名刺を削除しますか？")
                setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    if(Delete()){
                        Toast.makeText(context, "削除しました", Toast.LENGTH_LONG).show()
                    }
                })
                setNegativeButton("キャンセル    ", null)
                show()
            }
        }
    }
    fun Delete():Boolean{
        var bool = true
        return bool
    }
}
