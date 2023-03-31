package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.model.TopSellingItemEntity

class MockTopSellingLocalDataSource : TopSellingLocalDataSource {
    override fun getTopSellingItems(): List<TopSellingItemEntity> {
        return listOf(
            TopSellingItemEntity(
                3,
                "The Office Calendar",
                "https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/61LJk8D2udS._AC_UF894,1000_QL80_.jpg",
                "https://www.amazon.com/Calendar-Ink-Office-2022-Wall/dp/1645911772"
            ),
            TopSellingItemEntity(
                0,
                "Harry Potter Cushion",
                "https://cdn.shopify.com/s/files/1/0073/2010/9109/products/square-pillow-mockup-featuring-a-grey-fabric-couch-29003_5.jpg",
                "https://ghughutistore.com/products/harry-potter-cushion"
            ),
            TopSellingItemEntity(
                1,
                "The Spiderman Wall Poster",
                "https://ae01.alicdn.com/kf/S6e0928e4183b44a99ce1471b9ced331eV/Disney-Marvel-Spiderman-Poster-Design-Drawing-of-Spider-Man-Battle-Suit-Canvas-Painting-Wall-Art-Living.jpg_Q90.jpg_.webp",
                "https://pt.aliexpress.com/item/1005004624590113.html?gatewayAdapt=glo2bra"
            ),
            TopSellingItemEntity(
                2,
                "Friends Coffee Mug",
                "https://images-na.ssl-images-amazon.com/images/I/41kOhtt3U2L._SL500_._AC_SL500_.jpg",
                "https://www.kanbkam.com/ae/en/coffee-mugs-friends-tv-show-design-B07N6N432C"
            ),
        )
    }
}