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
    var CardId = arrayOf(0,0,0,0)
    //完全個人0
    var CompanyFlag = arrayOf(0,0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card_list)

        user_id = intent.getIntExtra("UserId",0)

        //何もしない（念のため用意しただけ）
        MyCardBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
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
            if(CompanyFlag[0] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[0])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[0])
                startActivity(intent)
            }
        }
        Name1.setOnClickListener{
            if(CompanyFlag[0] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[0])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[0])
                startActivity(intent)
            }
        }
        MyCard2.setOnClickListener{
            if(CompanyFlag[1] == 0) {
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[1])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[1])
                startActivity(intent)
            }
        }
        Name2.setOnClickListener{
            if(CompanyFlag[1] == 0) {
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[1])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[1])
                startActivity(intent)
            }
        }
        MyCard3.setOnClickListener{
            if(CompanyFlag[2] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[2])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[2])
                startActivity(intent)
            }
        }
        Name3.setOnClickListener{
            if(CompanyFlag[2] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[2])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[2])
                startActivity(intent)
            }
        }
        MyCard4.setOnClickListener{
            if(CompanyFlag[3] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[3])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[3])
                startActivity(intent)
            }
        }
        Name4.setOnClickListener{
            if(CompanyFlag[3] == 0){
                val intent = Intent(this,ShowIndividual::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[3])
                startActivity(intent)
            }else{
                val intent = Intent(this,ShowMyCard::class.java)
                intent.putExtra("UserId",user_id)
                intent.putExtra("CardId",CardId[3])
                startActivity(intent)
            }
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
        val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)

        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/list/create_card"
        URL.httpGet(listOf("user_id" to user_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    var cnt = 0
                    var name = ""
                    var NameText:TextView
                    var TemplateId = 0
                    for(i in 0..(json.length()-1)){
                        cnt++
                        name = (json[i] as JSONObject).get("name").toString()
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.setText(name)
                        TemplateId = (json[i] as JSONObject).get("img").toString().toInt()
                        MyCardArray[i].setImageResource(ImgArray[TemplateId-1])
                        CardId[i] = (json[i] as JSONObject).get("meisi_id").toString().toInt()
                        //企業個人が1,完全個人0
                        CompanyFlag[i] = (json[i] as JSONObject).get("flag").toString().toInt()
                        if(i==3){
                            break
                        }
                    }
                    if(cnt != 4){
                        for(i in 0..(3-cnt)){
                            MyCardArray[3-i].setImageResource(screen_background_light)
                            NameText = findViewById(NameArray[3-i]) as TextView
                            NameText.text = ""
                        }
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
