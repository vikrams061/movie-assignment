package movie.item

import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.xwray.groupie.viewbinding.BindableItem
import movie.assignment.R
import movie.assignment.databinding.ItemNameBinding

/**
 * Represents an item to be bound in a list with a name.
 *
 * @property name The name to be displayed in the item.
 */

internal class NameItem(private val name: String) : BindableItem<ItemNameBinding>() {
    /**
     * Binds the data to the view using the provided binding.
     *
     * @param binding The binding object for the item layout.
     * @param position The position of the item in the list.
     */
    override fun bind(binding: ItemNameBinding, position: Int) {
        binding.name.text = HtmlCompat.fromHtml(
            "<a href=\"https://en.wikipedia.org/wiki/${name.replace(" ", "_")}\">$name</a>",
            FROM_HTML_MODE_LEGACY
        )
    }

    /**
     * Initializes the view binding object for the item.
     *
     * @param view The view to be bound.
     * @return The initialized view binding object.
     */
    override fun initializeViewBinding(view: View): ItemNameBinding {
        return ItemNameBinding.bind(view).apply {
            name.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    /**
     * Returns the layout resource ID for the item.
     *
     * @return The layout resource ID.
     */
    override fun getLayout(): Int = R.layout.item_name

    /**
     * Returns the span size for the item in a grid layout.
     *
     * @param spanCount The total number of spans in the grid.
     * @param position The position of the item in the list.
     * @return The span size for the item.
     */
    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return spanCount / 3
    }
}
