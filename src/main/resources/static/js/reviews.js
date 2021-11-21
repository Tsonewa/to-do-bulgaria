const tripId = document.getElementById('tripId').value;

const reviewsContainer =
    document.getElementById("comments-list");

const reviewsArray = [];

    const displayReviews = (reviews) => {
    reviewsContainer.innerHTML = reviews.map((r) => {
        return asReview(r)
    }
    ).join('');
}

    function asReview(r){
    let li = document.createElement('li');
    let commantMainLevel = document.createElement('div');
    commantMainLevel.className = "comment-main-level";

    let avatarDiv = document.createElement('div');
    avatarDiv.className = "comment-avatar";
    let avatar = document.createElement('img');
    avatar.src = `${r.profilePictureUrl}`;
    avatarDiv.append(avatar);

    let commentBox = document.createElement('div');
    commentBox.className = "comment-box";

    let commantHeadDiv = document.createElement('div');
    commantHeadDiv.className = "comment-head";
    let h6 = document.createElement('h6');
        h6.className = "comment-name by-author";
        h6.innerText = `${r.user}`
        let span = document.createElement('span');
        span.innerText = `${r.createdOn}`;

        commantHeadDiv.append(h6);
        commantHeadDiv.append(span);

        let commentContentDiv = document.createElement('div');
        commentContentDiv.className = "comment-content";
        commentContentDiv.textContent = `${r.message}`

        commentBox.append(commantHeadDiv);
        commentBox.append(commentContentDiv);

        commantMainLevel.append(avatarDiv);
        commantMainLevel.append(commentBox);

        li.append(commantMainLevel);

        return li.innerHTML;
}

fetch(`http://localhost:8080/api/${tripId}/reviews`)
    .then(response => response.json())
    .then(data => {
        for (let review of data){
            reviewsArray.push(review);
        }
        displayReviews(reviewsArray);
    });


