let genreState = 1;

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

function toggleGenreSelection() {
  switch (genreState) {
    case 0:
      bookGenreSelection();
      genreState = 1;

      break;
    case 1:
      shopGenreSelection();
      genreState = 0;

      break;
  }
}

function bookGenreSelection() {
  let content = document.getElementById("collapsableBoxGenresContent");

  document.getElementById("contentTextBox").innerHTML = "Inventory";

  const bookGenresRaw = Cookies.get("genres");

  const allGenres = JSON.parse(bookGenresRaw);

  console.log("json in inventory" + allGenres);

  removeChildrenFromElement(content);

  for (let i = 0; i < allGenres.length; i++) {

    let newCard = document.createElement("div");
    newCard.setAttribute("id", "bookCard" + i);
    newCard.classList.add("genericCardInAccordion", "zoom");

    newCard.style.color = "#F6AE2D";

    let genreIdAnchor = "genreIdAnchor" + +i;
    let bookIdAnchor = "bookIdAnchor" + +i;

    newCard.innerHTML =
        "<div>" +
        "<p id='" + genreIdAnchor + "' style='display: none'>"
        + allGenres[i]["id"] + "</p>" +
        "<p id='" + bookIdAnchor + "' style='display: none'>"
        + getBookId() + "</p>" +
        "<p>Name: " + '<br>' + allGenres[i]["name"] + "</p>" +
        "</div>";

    content.addEventListener("click", new function () {
      newCard.onclick = function () {
        const genreId = document.getElementById(genreIdAnchor).innerHTML;
        const bookId = document.getElementById(bookIdAnchor).innerHTML;

        document.getElementById("genreIdField").value = genreId;
        document.getElementById("bookIdField").value = bookId;
        document.getElementById("typeField").value = "RemoveFromBook";

        let form = document.getElementById("hidden-book-form");

        form.submit();

      }
    });

    content.appendChild(newCard);
  }
}

function shopGenreSelection() {
  let content = document.getElementById("collapsableBoxGenresContent");

  document.getElementById("contentTextBox").innerHTML = "Shop";

  const allGenresRaw = Cookies.get("allGenres");

  const allGenres = JSON.parse(allGenresRaw);

  removeChildrenFromElement(content);

  for (let i = 0; i < allGenres.length; i++) {

    let newCard = document.createElement("div");
    newCard.setAttribute("id", "bookCard" + i);
    newCard.classList.add("genericCardInAccordion", "zoom");

    newCard.style.color = "#F6AE2D";

    let genreIdAnchor = "genreIdAnchor" + +i;
    let bookIdAnchor = "bookIdAnchor" + +i;

    newCard.innerHTML =
        "<div>" +
        "<p id='" + genreIdAnchor + "' style='display: none'>"
        + allGenres[i]["id"] + "</p>" +
        "<p id='" + bookIdAnchor + "' style='display: none'>"
        + getBookId() + "</p>" +
        "<p>Name: " + '<br>' + allGenres[i]["name"] + "</p>" +
        "</div>";

    content.addEventListener("click", new function () {
      newCard.onclick = function () {
        const genreId = document.getElementById(genreIdAnchor).innerHTML;
        const bookId = document.getElementById(bookIdAnchor).innerHTML;

        if (isGenrePresentInBook(genreId)){
          document.getElementById("errorTextContainer").innerHTML = "Genre already present in book";

          return;
        }
        else{
          document.getElementById("errorTextContainer").innerHTML = "";
        }

        document.getElementById("genreIdField").value = genreId;
        document.getElementById("bookIdField").value = bookId;
        document.getElementById("typeField").value = "addToBook";

        let form = document.getElementById("hidden-book-form");

        form.submit();
      }
    });

    content.appendChild(newCard);
  }
}

function isGenrePresentInBook(genreId){
  const bookGenresJson = Cookies.get("genres");
  const bookGenresArray = JSON.parse(bookGenresJson);

  for (let i = 0; i < bookGenresArray.length; i++){
    if (bookGenresArray[i]["id"] === parseInt(genreId)){
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
