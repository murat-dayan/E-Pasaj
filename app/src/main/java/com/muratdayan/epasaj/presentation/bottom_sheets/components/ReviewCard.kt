package com.muratdayan.epasaj.presentation.bottom_sheets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.components.TextItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewCard(
    rating: Int,
    comment: String,
    date: String,
    reviewerName: String,
    reviewerEmail: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(colorResource(id = R.color.md_theme_background)),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.md_theme_background))
                .padding(16.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = colorResource(id = R.color.md_theme_tertiaryFixed)
                )
                TextItem(
                    text = "$rating",
                    fontSizeRes = R.dimen.text_size_h3_20,
                    textColor = colorResource(id = R.color.md_theme_primary)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextItem(
                text = "$reviewerName : $comment",
                fontSizeRes = R.dimen.text_size_h3_20,
                textColor = colorResource(id = R.color.md_theme_primary)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextItem(
                text = "${formatDate(date)}",
                fontSizeRes = R.dimen.text_size_h5_16,
                textColor = colorResource(id = R.color.md_theme_onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextItem(
                text = "$reviewerEmail",
                fontSizeRes = R.dimen.text_size_h6_14,
                textColor = colorResource(id = R.color.md_theme_onSurfaceVariant)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReviewCardPreview(){
    ReviewCard(
        rating = 123,
        comment = "dasd",
        date = "dasda",
        reviewerName = "dasds",
        reviewerEmail = "dsadad"
    )
}


// Tarih formatını ayarlamak için yardımcı fonksiyon
private fun formatDate(dateString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDate = dateFormat.parse(dateString)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
    return outputFormat.format(parsedDate ?: Date())
}