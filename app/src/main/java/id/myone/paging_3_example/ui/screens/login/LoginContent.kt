/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.paging_3_example.R
import id.myone.paging_3_example.common.VoidCallback
import id.myone.paging_3_example.ui.componens.ButtonComponent
import id.myone.paging_3_example.ui.componens.OAuthButtonLoginComponent
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme
import id.myone.paging_3_example.ui.theme.plusJakartaSans

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isEnabledButton: Boolean = false,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onPressLoginWithCredential: VoidCallback,
    onPressLoginWithGoogleAccount: VoidCallback,
    onNavigateToRegister: VoidCallback,
    onNavigateToForgotPassword: VoidCallback,
) {
    val scrollState = rememberScrollState()
    var isShowPassword by remember { mutableStateOf(false) }
    val localFocus = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = plusJakartaSans,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onChangeEmail,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Your email address", style = MaterialTheme.typography.bodySmall, fontFamily= plusJakartaSans)
            },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                autoCorrect = false,
            ),

            )
        OutlinedTextField(
            value = password,
            onValueChange = onChangePassword,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Your password", style = MaterialTheme.typography.bodySmall, fontFamily= plusJakartaSans)
            },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(onDone = {
                localFocus.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                autoCorrect = false,
            ),
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isShowPassword = !isShowPassword }) {
                    Icon(
                        painter = painterResource(
                            id = if (isShowPassword) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24
                        ),
                        contentDescription = null,
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(
                onClick = onNavigateToForgotPassword,
            ) {
                Text(
                    "Forgot Password?",
                    textAlign = TextAlign.Right,
                    fontFamily = plusJakartaSans,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        ButtonComponent(
            name = "LOGIN",
            isEnabled = isEnabledButton,
            onClick = onPressLoginWithCredential
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(modifier = Modifier.weight(3f))
            Text(
                "OR",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        OAuthButtonLoginComponent(
            icon = R.drawable.google_icon,
            name = "Login With Google",
            backgroundColor = Color(0xffF1F6F7),
            textColor = Color.Black,
            onClick = onPressLoginWithGoogleAccount,
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Don't Have an Account?",
                fontSize = 14.sp,
                fontFamily = plusJakartaSans,
            )

            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Register",
                    fontSize = 14.sp,
                    fontFamily = plusJakartaSans,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}


@Preview(backgroundColor = 0xffff, showBackground = true, widthDp = 460, heightDp = 960)
@Composable
fun LoginContentPreview() {
    Paging3exampleTheme {
        LoginContent(email = "example@gmail.com",
            password = "12345678",
            onChangeEmail = {},
            onChangePassword = {},
            onPressLoginWithCredential = {},
            onPressLoginWithGoogleAccount = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {}
        )
    }
}