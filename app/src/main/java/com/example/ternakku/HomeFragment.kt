package com.example.ternakku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var categoryGrid: RecyclerView
    private lateinit var featuredRecyclerView: RecyclerView
    private lateinit var appNameTextView: TextView
    private lateinit var menuIcon: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize Views
        bannerViewPager = rootView.findViewById(R.id.bannerViewPager)
        categoryGrid = rootView.findViewById(R.id.categoryGrid)
        featuredRecyclerView = rootView.findViewById(R.id.featuredRecyclerView)
        appNameTextView = rootView.findViewById(R.id.appName)
        menuIcon = rootView.findViewById(R.id.menuIcon)

        // Setup ViewPager for Banner Carousel
        val bannerImages = listOf(
            R.drawable.banner1,  // Gambar pertama
            R.drawable.banner2   // Gambar kedua
        )

        // Set adapter untuk ViewPager2
        val bannerAdapter = BannerAdapter(bannerImages)
        bannerViewPager.adapter = bannerAdapter

        // Setup RecyclerView for Categories
        categoryGrid.layoutManager = GridLayoutManager(requireContext(), 4)
        val categoryAdapter = CategoryAdapter(getCategoryData()) // Replace with your category data
        categoryGrid.adapter = categoryAdapter

        // Setup RecyclerView for Featured Items
        featuredRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val featuredAdapter = FeaturedAdapter(getFeaturedData()) // Replace with your featured items data
        featuredRecyclerView.adapter = featuredAdapter

        return rootView
    }

    // Sample data methods for banner, category, and featured items
    private fun getBannerData(): List<String> {
        // Replace with your actual banner data

        return listOf("Banner 1", "Banner 2", "Banner 3")
    }

    private fun getCategoryData(): List<String> {
        // Replace with your actual category data
        return listOf("Category 1", "Category 2", "Category 3", "Category 4")
    }

    private fun getFeaturedData(): List<String> {
        // Replace with your actual featured data
        return listOf("Featured 1", "Featured 2", "Featured 3")
    }
}