const puppeteer = require("puppeteer");
const URL = "https://www.zoro.com/abrasive-parts/c/9613/";

async function scrape(url){
    try{
        const USER_AGENT = 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36';
        const browser = await puppeteer.launch({
            headless: false, // zoro blocks headless mode
            defaultViewport: null, // fullsize for menu spec
            args: ["--window-size=1920,1080"]
        })

        const page = await browser.newPage()
        await page.setUserAgent(USER_AGENT); // trying headless, doesn't work yet
        await page.goto(url);
        const items = new Map();
        const numberOfPages = await page.$eval("#search-pagination-selector + span", span => span.textContent.trim().charAt(3));


        for (let i = 1; i <= numberOfPages; i++) {
            if (i > 1){
                await page.goto(url + "?page=" + (i))
            }
            const searchResultSection = await page.waitForSelector("section.search-results");
            const resultDivs = await searchResultSection.$$("div.search-result");


            for (let resultDiv of resultDivs) {
                let contentElement = await resultDiv.$("div.product-card > div.content");
                let imageElement = await contentElement.$("div.product-image-with-placeholder__image > div.v-image__image");
                let imageUrl = await imageElement.evaluate(div => div.style.backgroundImage)
                let titleElement = await contentElement.$("div.product-info-container div.product-title a");
                let brandName = await titleElement.$eval("strong", strong => strong.textContent.trim());
                let productName = await titleElement.$eval("span", span => span.textContent.trim());
                let link = await titleElement.evaluate(a => a.href);
                let productId = await contentElement.$eval("div.zoro-mfr-no div", div => div.textContent.trim());
                let price = await contentElement.$eval("div.product-price", div => div.textContent.trim());
                let availability;
                try {
                    availability = await contentElement.$eval("div.badge", div => div.textContent.trim());
                } catch (e) {
                    availability = null;
                }
                let shippingComment = await contentElement.$eval("div.avl-lead-time-badge span", span => span.textContent.trim());
                items.set(productId, {
                    productId: productId,
                    brandName: brandName,
                    productName: productName,
                    link: link,
                    image: imageUrl.replaceAll('url("', "").replaceAll('")', ""),
                    price: price,
                    availability: availability,
                    shippingComment: shippingComment
                })
            }




        }
        await browser.close();
    } catch (e) {
        console.error(e);
    }
}

scrape(URL);