function fillBooks() {
    let newList = document.getElementById("bookList");
    const booksJson = Cookies.get("allBooks");
    const books = JSON.parse(booksJson);

    for (let i = 0; i < books.length; i++) {
        console.log("iterationNumber: " + i);
        let newCard = document.createElement("div");
        newCard.setAttribute("id", "BookCard" + i);
        newCard.classList.add("listCard", "zoom");
        newCard.style.color = "#F6AE2D";
        let bookIdAnchor = "bookIdAnchor" + +i;


        newCard.innerHTML =
            "<div>" +
            "<p id='" + bookIdAnchor + "' style='display: none'>"
            + books[i]["id"] + "</p>" +
            "<p>" + '<br>' + books[i]["name"] + "</p>" +
            "</div>";

        newList.addEventListener("click", new function () {
            newCard.onclick = function () {
                const books = document.getElementById(
                    bookIdAnchor).innerHTML;

                document.getElementById("idField").value = books;
                document.getElementById("typeField").value = "show";

                let form = document.getElementById("hidden-book-form");
                form.submit();
            }
        });


        newList.appendChild(newCard);
    }
}
