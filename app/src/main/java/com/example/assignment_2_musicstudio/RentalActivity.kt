import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String,
    val rating: Int,
    val attributes: String,  // Changed to list for multi-choice
    val price: Int,
    val imageResId: Int
) : Parcelable
