package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_my_card_list.*
import org.json.JSONObject
import android.R.drawable.*
import android.widget.TextView
import jp.ac.asojuku.st.demeshi.R.drawable.*
import jp.ac.asojuku.st.demeshi.R.id.*

class MyCardList : AppCompatActivity() {

    var user_id = 0
    var card_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card_list)

        user_id = intent.getIntExtra("UserId",0)

        //何もしない（念のため用意しただけ）
        MyCardBtn.setOnClickListener{
            startActivity(Intent(it.context,MyCardList::class.java))
        }
        HaveCardBtn.setOnClickListener{
            val intent = Intent(this,HaveCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        PhotoCardBtn.setOnClickListener{
            val intent = Intent(this,PhotoCard::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        AddInd.setOnClickListener{
            val intent = Intent(this,Template::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        MyCard1.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        Name1.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        MyCard2.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        Name2.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        MyCard3.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        Name3.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        MyCard4.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        Name4.setOnClickListener{
            val intent = Intent(this,ShowMyCard::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            startActivity(intent)
        }
        //CompanyName.setOnClickListener{startActivity(Intent(it.context,ShowMyCard::class.java))}
        GetMyCard()
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
                        //Toast.makeText(context, "退会しました", Toast.LENGTH_LONG).show()
                        val URL:String = "http://18001187.pupu.jp/untitled/public/user/delete"
                        URL.httpGet(listOf("user_id" to user_id)).responseJson() { request, response, result ->
                            when (result) {
                                is Result.Success -> {
                                    // レスポンスボディを表示
                                    val json = result.value.obj()
                                    val results = json.get("result")// as JSONArray
                                    if(results == 1){
                                        Toast.makeText(context, "退会しました", Toast.LENGTH_LONG).show()
                                        startActivity(intent)
                                    }
                                    else{
                                        Toast.makeText(context, "サーバに問題があり、退会できませんでした", Toast.LENGTH_LONG).show()
                                    }
                                }
                                is Result.Failure -> {
                                    println("通信に失敗しました。")
                                }
                            }
                        }
                    })
                    setNegativeButton("キャンセル", null)
                    show()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    //作成した名刺一覧
    fun GetMyCard(){
        //val URL:String = "http://18001187.pupu.jp/untitled/public/card/allget"
        val NameArray = arrayOf(R.id.Name1, R.id.Name2,R.id.Name3, R.id.Name4)
        val MyCardArray = arrayOf(MyCard1,MyCard2,MyCard3,MyCard4)
        val ImgArray = arrayOf(green,f4796,f4788,f4786,f4790,f4791,space,f4782,f4792)
        val CallArray = arrayOf(CallBtn1,CallBtn2,CallBtn3,CallBtn4)
        val MailArray = arrayOf(MailBtn1,MailBtn2,MailBtn3,MailBtn4)

        val URL:String = "http://18001187.pupu.jp/untitled/public/card/allget/" + user_id.toString()
        URL.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    var cnt = 0
                    var name = ""
                    var NameText:TextView
                    var TemplateId = 0
                    for(i in 0..(json.length())){
                        cnt++
                        name = (json[i] as JSONObject).get("value").toString()
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.setText(name)
                        TemplateId = (json[i] as JSONObject).get("template_id").toString().toInt()
                        MyCardArray[i].setImageResource(ImgArray[TemplateId])
                        if(i==3){
                            break
                        }
                    }
                    for(i in 3..cnt -1){
                        //力技（実質）動的リスト化
                        MyCardArray[i].setImageResource(screen_background_light)
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.text = ""
                        CallArray[i].text = ""
                        CallArray[i].background = null
                        MailArray[i].text = ""
                        MailArray[i].background = null
//                        MyCard2.setImageResource(screen_background_light)
//                        Name2.text = ""
//                        CallBtn2.text = ""
//                        CallBtn2.background = null
//                        MailBtn2.text = ""
//                        MailBtn2.background = null
                    }
//                    val json = result.value.obj()
//                    val results = json.get("result")// as JSONArray
//                    if(results == 1){
//                        //取得したもので処理
//                        //名刺ごとの名刺IDをintentでShowMyCardに渡す
//                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
