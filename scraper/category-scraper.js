const puppeteer = require('puppeteer')

async function getVisual() {
    try {
        const USER_AGENT = 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36';
        const URL = 'https://www.zoro.com'
        const browser = await puppeteer.launch({
            headless: false, // zoro blocks headless mode
            defaultViewport: null, // fullsize for menu spec
            args: ["--window-size=1920,1080"]
        })

        const page = await browser.newPage()
        await page.setUserAgent(USER_AGENT); // trying headless, doesn't work yet
        await page.goto(URL);
        await page.click('button.category-navigation-button[aria-label="Open Menu"]'); //open menu for rendering nodes

        const menuZeroList = await page.$$("ul.menu--level-zero > li"); // first level
        const links = new Map();

        for (let menuZeroItem of menuZeroList) {
            let button = await menuZeroItem.$("button");
            try {
                await button.hover(); //hover for rendering sublevels
            } catch (e) {

            }
            let navPanel = await menuZeroItem.$("div.navigation-panel--level-one");
            await navPanel.waitForSelector("ul.menu--level-two");
            let menuTwos = await navPanel.$$("ul.menu--level-two");
            for (let menuTwo of menuTwos) {
                let sublinks = await menuTwo.$$("a");
                for (let element of sublinks) {
                    let info = await extractLinkInfo(element);
                    if (!info) continue;
                    links.set(info.text, info.href);
                }
            }
        }


        await browser.close()
    } catch (error) {
        console.error(error)
    }
}

async function extractLinkInfo(element) {
    let text = await element.$eval("span", span => span.textContent);
    let link = await element.evaluate(a => a.href);
    return {
        text: text.trim(), href: link
    };
}

getVisual()