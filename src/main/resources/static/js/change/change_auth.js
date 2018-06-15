let authState = 1;

function getBookId() {
    const bookJson = Cookies.get("book");

    const book = JSON.parse(bookJson);

    const returnValue = book["id"];

    return returnValue;
}

function fillBookData() {
    const bookJson = Cookies.get("book");

    const book = JSON.parse(bookJson);

    console.log(book);

    document.getElementById("bookNameField")
        .innerText += " " + book["name"];

    document.getElementById("bookDescriptionField")
        .innerText += " " + book["description"];

    document.getElementById("bookPriceField")
        .innerText += " " + book["price"];
}

function toggleAuthSelection() {
    switch (authState) {
        case 0:
            bookAuthSelection();
            authState = 1;

            break;
        case 1:
            shopAuthSelection();
            authState = 0;

            break;
    }
}

function bookAuthSelection() {
    let content = document.getElementById("collapsableBoxAuthContent");

    document.getElementById("contentTextBox").innerHTML = "Inventory";

    const bookAuthRaw = Cookies.get("auth");

    const allAuth = JSON.parse(bookAuthRaw);


    removeChildrenFromElement(content);

    for (let i = 0; i < allAuth.length; i++) {

        let newCard = document.createElement("div");
        newCard.setAttribute("id", "bookCard" + i);
        newCard.classList.add("genericCardInAccordion", "zoom");

        newCard.style.color = "#F6AE2D";

        let authIdAnchor = "authIdAnchor" + +i;
        let bookIdAnchor = "bookIdAnchor" + +i;

        newCard.innerHTML =
            "<div>" +
            "<p id='" + authIdAnchor + "' style='display: none'>"
            + allAuth[i]["id"] + "</p>" +
            "<p id='" + bookIdAnchor + "' style='display: none'>"
            + getBookId() + "</p>" +
            "<p>Name: " + '<br>' + allAuth[i]["name"] + "</p>" +
            "<p>SurName: " + '<br>' + allAuth[i]["surname"] + "</p>" +
            "</div>";

        content.addEventListener("click", new function () {
            newCard.onclick = function () {
                const authId = document.getElementById(authIdAnchor).innerHTML;
                const bookId = document.getElementById(bookIdAnchor).innerHTML;

                document.getElementById("authIdField").value = authId;
                document.getElementById("bookIdField").value = bookId;
                document.getElementById("typeField").value = "RemoveFromBook";

                let form = document.getElementById("hidden-book-form");

                form.submit();

            }
        });

        content.appendChild(newCard);
    }
}

function shopAuthSelection() {
    let content = document.getElementById("collapsableBoxAuthContent");

    document.getElementById("contentTextBox").innerHTML = "Shop";

    const allAuthRaw = Cookies.get("allAuth");

    const allAuth = JSON.parse(allAuthRaw);

    removeChildrenFromElement(content);

    for (let i = 0; i < allAuth.length; i++) {

        let newCard = document.createElement("div");
        newCard.setAttribute("id", "bookCard" + i);
        newCard.classList.add("genericCardInAccordion", "zoom");

        newCard.style.color = "#F6AE2D";

        let authIdAnchor = "authIdAnchor" + +i;
        let bookIdAnchor = "bookIdAnchor" + +i;

        newCard.innerHTML =
            "<div>" +
            "<p id='" + authIdAnchor + "' style='display: none'>"
            + allAuth[i]["id"] + "</p>" +
            "<p id='" + bookIdAnchor + "' style='display: none'>"
            + getBookId() + "</p>" +
            "<p>Name: " + '<br>' + allAuth[i]["name"] + "</p>" +
            "<p>SurName: " + '<br>' + allAuth[i]["surname"] + "</p>" +
            "</div>";

        content.addEventListener("click", new function () {
            newCard.onclick = function () {
                const authId = document.getElementById(authIdAnchor).innerHTML;
                const bookId = document.getElementById(bookIdAnchor).innerHTML;

                if (isAuthPresentInBook(authId)) {
                    document.getElementById("errorTextContainer").innerHTML = "Author already present in book";

                    return;
                }
                else {
                    document.getElementById("errorTextContainer").innerHTML = "";
                }

                document.getElementById("authIdField").value = authId;
                document.getElementById("bookIdField").value = bookId;
                document.getElementById("typeField").value = "addToBook";

                let form = document.getElementById("hidden-book-form");

                form.submit();
            }
        });

        content.appendChild(newCard);
    }
}

function isAuthPresentInBook(AuthId) {
    const bookAuthJson = Cookies.get("auth");
    const bookAuthArray = JSON.parse(bookAuthJson);

    for (let i = 0; i < bookAuthArray.length; i++) {
        if (bookAuthArray[i]["id"] === parseInt(AuthId)) {
            return true;
        }
    }

    return false;
}

function removeChildrenFromElement(nodeWithChildren) {

    while (nodeWithChildren.firstChild) {
        nodeWithChildren.removeChild(nodeWithChildren.firstChild);
    }
}
