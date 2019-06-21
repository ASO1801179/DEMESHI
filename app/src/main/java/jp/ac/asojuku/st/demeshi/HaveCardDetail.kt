package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_have_card_detail.*

class HaveCardDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_detail)
        Back.setOnClickListener{startActivity(Intent(it.context,HaveCardList::class.java))}
        Delete.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("名刺削除")
                setMessage("本当に名刺を削除しますか？")
                setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                    Toast.makeText(context, "削除しました", Toast.LENGTH_LONG).show()
                })
                setNegativeButton("キャンセル", null)
                show()
            }
        }
    }
}
