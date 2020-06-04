// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
window.onload = async () => {
    getComments();
    attachCommentFormSubmitEvent();
}

const getComments = async () => {
    const response = await fetch('/comment');
    const comments = JSON.parse(await response.text());
    const pTagList= comments.map((comment)=>{
        const commentText = document.createElement("P");
        commentText.innerText=comment.commentText;
        commentText.className="comment"
        const timestamp = document.createElement("P");
        timestamp.innerText=convertUTCToString(comment.timestamp);
        timestamp.className="timestamp"
        return {commentText, timestamp};
    });
    const divList = pTagList.map((ptags)=>{
        const div = document.createElement("DIV");
        div.className="commentDiv py-2 px-4 d-flex justify-content-between";
        div.appendChild(ptags.commentText);
        div.appendChild(ptags.timestamp);
        return div;
    });
    const commentsContainer = document.getElementById("commentsContainer");
    commentsContainer.innerHTML='';
    divList.forEach((div)=>{
        commentsContainer.appendChild(div);
    });
}


const addComment = async (event) => {
    event.preventDefault();
    await fetch('/comment', {
        method: 'post',
        body: new URLSearchParams({ "commentText": document.getElementById('commentText').value })
    });

    getComments();
}

const attachCommentFormSubmitEvent = () => {
    document.getElementById("addCommentForm").addEventListener("submit", addComment);
}

const convertUTCToString = (utc) => {
    const date = new Date(utc);
    return date.getHours() + ":"  
        + date.getMinutes() + " "
        + date.getDate() + "/"
        + (date.getMonth()+1)  + "/" 
        + date.getFullYear();
}