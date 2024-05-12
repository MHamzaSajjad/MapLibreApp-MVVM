package com.mhamzasajjad.mapapp.presentation.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.mhamzasajjad.mapapp.R
import com.mhamzasajjad.mapapp.presentation.theme.MapApp_Typography
import com.mhamzasajjad.mapapp.presentation.theme.appPrimaryColorLightTheme


@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
    val userProfileDetails by viewModel.userProfileDetails.collectAsState()
    val lastPickedPicture by viewModel.lastPickedPicture.collectAsState()
    val toastText by viewModel.toastText.collectAsState()
    val showToast by viewModel.showToast.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 8.dp),
            text = stringResource(id = R.string.edit_profile),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        PicturePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = lastPickedPicture,
            onSelection = { uri ->
                uri?.let {
                    viewModel.onProfilePictureChanged(it)
                }
            }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            text = stringResource(id = R.string.about_me),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        ProfileTextField(
            labelText = stringResource(id = R.string.full_name),
            fieldValue = userProfileDetails.fullName,
            keyboardType = KeyboardType.Text,
            onValueChange = {
                viewModel.onFullNameChanged(it)
            }
        )

        ProfileBirthdayField(
            labelText = stringResource(id = R.string.birthday),
            fieldValue = userProfileDetails.birthday,
            keyboardType = KeyboardType.Text,
            onClicked = {
                viewModel.onBirthdayFieldClicked()
            }
        )

        ProfileTextField(
            labelText = stringResource(id = R.string.email),
            fieldValue = userProfileDetails.email,
            keyboardType = KeyboardType.Email,
            onValueChange = {
                viewModel.onEmailChanged(it)
            }
        )

        ProfileTextField(
            labelText = stringResource(id = R.string.phone),
            fieldValue = userProfileDetails.phone,
            keyboardType = KeyboardType.Phone,
            onValueChange = {
                viewModel.onPhoneChanged(it)
            }
        )

        val isChecked by viewModel.isSubscribed.collectAsState()
        val interactionSource = remember { MutableInteractionSource() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    viewModel.onSubscribedChanged()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = null
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                text = stringResource(id = R.string.subscribe_newsletter),
                style = MapApp_Typography.body1,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = appPrimaryColorLightTheme
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                viewModel.onSaveChangesClicked()
            }) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(R.string.save_changes),
                style = MapApp_Typography.body1,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        if (showToast) {
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            viewModel.hideToast()
        }

        if (showDatePicker) {
            DatePickerWithDialog(
                setShowDatePicker = { isShow ->
                    viewModel.setShowDatePicker(isShow)
                },
                onBirthdayChanged = { birthday ->
                    viewModel.onBirthdayChanged(birthday)
                }
            )
        }
    }

}

@Composable
fun ProfileTextField(
    labelText: String,
    fieldValue: String,
    keyboardType: KeyboardType,
    onValueChange: ((String) -> Unit)
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        value = fieldValue,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = labelText,
                style = MapApp_Typography.body1
            )
        },
        textStyle = MapApp_Typography.body1,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = appPrimaryColorLightTheme,
            unfocusedIndicatorColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun DateRangeIcon() {
    Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = "Date Range",
        tint = appPrimaryColorLightTheme
    )
}

@Composable
fun ProfileBirthdayField(
    labelText: String,
    fieldValue: String,
    keyboardType: KeyboardType,
    onClicked: (() -> Unit)
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        value = fieldValue,
        onValueChange = {},
        label = {
            Text(
                text = labelText,
                style = MapApp_Typography.body1
            )
        },
        textStyle = MapApp_Typography.body1,
        interactionSource = interactionSource.also { intSource ->
            LaunchedEffect(intSource) {
                intSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        onClicked()
                    }
                }
            }
        },
        maxLines = 1,
        trailingIcon = { DateRangeIcon() },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = appPrimaryColorLightTheme,
            unfocusedIndicatorColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun PicturePickerView(
    modifier: Modifier,
    lastSelectedImage: Uri?,
    onSelection: (Uri?) -> Unit
) {

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        onSelection(it)
    }
    Image(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable {
                galleryLauncher.launch("image/*")
            },
        painter = rememberAsyncImagePainter(
            model = lastSelectedImage,
            placeholder = painterResource(id = R.drawable.profile_account),
            error = painterResource(id = R.drawable.profile_account)
        ),
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    modifier: Modifier = Modifier,
    setShowDatePicker: (Boolean) -> Unit,
    onBirthdayChanged: (String) -> Unit
) {
    val datePickerState =
        rememberDatePickerState(
            yearRange = 1900..2024
        )

    DatePickerDialog(
        onDismissRequest = { setShowDatePicker(false) },
        confirmButton = {
            TextButton(
                onClick = { setShowDatePicker(false) },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { setShowDatePicker(false) }) {
                Text(text = "Cancel")
            }
        }) {
        onBirthdayChanged(
            DatePickerDefaults.dateFormatter()
                .formatDate(
                    dateMillis = datePickerState.selectedDateMillis,
                    locale = CalendarLocale.US
                ) ?: ""
        )
        DatePicker(state = datePickerState)
    }
}