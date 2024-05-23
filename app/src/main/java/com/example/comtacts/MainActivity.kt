package com.example.comtacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.comtacts.ui.theme.ComtactsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComtactsTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val context = LocalContext.current

        val db = remember {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contacts.db"
            )
                .build()//database name 은 어떤 database 가져올건지  정하는거고 contact.db ( 프로젝트 이름.db)이런식으로 사용 - 한번 정하면 바뀌지않음
        }
        //val list = db.userDao().getAll()

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var lastname by remember { mutableStateOf("") }
            OutlinedTextField(value = lastname,
                onValueChange = { lastname = it },
                label = {
                    Text(text = "이름 : ")
                }
            )
            var phonenum by remember { mutableStateOf("") }
            OutlinedTextField(value = phonenum,
                onValueChange = { phonenum = it },
                label = {
                    Text(text = "전화번호 : ")
                }
            )
            var emailaddress by remember { mutableStateOf("") }
            OutlinedTextField(value = emailaddress,
                onValueChange = { emailaddress = it },
                label = {
                    Text(text = "이메일 : ")
                }
            )

            var showInfo by remember { mutableStateOf(false) } //command+option+v => 함수명을 변수로 빼는 단축키
            val coroutineScope = rememberCoroutineScope()//스레드 관리를 위한 새로운 스레구임
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) { //룸에서 쓰고 가져오니까 코루틴에서 IO 기본 제공
                        db.userDao().insertAll(//insert할때 자동 증가가 되기때문에 uid는 필요가 없다.
                            User(//클래스의 객체생성
                                userName = lastname,
                                phoneNum = phonenum,
                                email = emailaddress
                            ) //email은 User.kt의 속성(attribute_매개변수)값, email은
                        )
                    }//코루틴 스코프에서 실행한다.
//이렇게 메인 스레드가 아닌 ROOM의 IO를 만들면 잘 동작한다. view app inspection에서 우리가 활영한 db데이터를 볼수있다. (누른만큼 추가됌)
                    showInfo = !showInfo
                }) {
                Text(text = "등록")
            }
            if (showInfo) {
                Text(text = "이름: $lastname")
                Text(text = "전화번호: $phonenum")
                Text(text = "이메일: $emailaddress")
            } else {
                Text(text = "입력해주세요")
            }
            Column {
                val userList by db.userDao().getAll().collectAsState(initial = emptyList())
                //userDao에서 데이터를 가져와 리스트에 넣어준다, state는 상태관리 언어 , 기본값은 listof로 기존 list넣어도되고 빈 리스트 emptylist넣어도 됌
                for(user in userList){
                    Text(text = "이름 : ${lastname}")
                    Text(text = "폰번호 : ${phonenum}") //여기는
                    Text(text = "이메일 : ${emailaddress}")
                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        ComtactsTheme {
            MainScreen()
        }
    }
}


