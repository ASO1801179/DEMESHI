package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_have_card_list.*
import android.R.drawable.*
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class HaveCardList : AppCompatActivity() {

    val user_id = intent.getIntExtra("UserId",0)
    var card_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_list)
        MyCardBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCardBtn.setOnClickListener{
            startActivity(Intent(it.context,HaveCardList::class.java))
        }
        PhotoCardBtn.setOnClickListener{
            val intent = Intent(this,PhotoCard::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCard.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        Name.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }

        //力技（実質）動的リスト化
//        MyCard2.setImageResource(screen_background_light)
//        Name2.text = ""
//        CallBtn2.text = ""
//        CallBtn2.background = null
//        MailBtn2.text = ""
//        MailBtn2.background = null
    }

    //所持している名刺一覧
    fun GetMyCard(){
        val URL:String = "http://18001187.pupu.jp/untitled/public/card/collection_return"
        //val URL:String = "http://18001187.pupu.jp/untitled/public/card/collection_return/" + user_id
        URL.httpGet(listOf("user_id" to user_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        //取得したもので処理
                        //名刺ごとの名刺IDをintentでShowMyCardに渡す
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
