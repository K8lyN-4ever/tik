import {getDownloadUrl} from '../../../../webProject/Tiktok-web/js/util/myOss.js'

$(document).ready(function() {


    function createPostTemplate(username, avatarUrl, caption, videoUrl, likesCount, commentsCount) {
        return `
    <div class="post">
      <div class="post-info">
        <div class="user">
          <img src="${avatarUrl}" alt="avatar">
          <div>
            <h6>${username}</h6>
            <p>${caption}</p>
            <i class="fa-solid fa-music-note"></i>
            <marquee>original sound - ${username}</marquee>
          </div>
        </div>
        <button id="btnText1">关注</button>
      </div>
      <div class="post-content">
        <video class="videos" autoplay muted controls loop disablepictureinpicture controlslist="nodownload noplaybackrate">
          <source src="${videoUrl}" type="video/mp4">
        </video>
        <div class="video-icons">
          <a href="#"><i class="heart1">🤍</i><span id="love1">${likesCount}</span></a>
          <a href="#"><i class="fas fa-comment-dots fa-lg"></i><span>${commentsCount}</span></a>
        </div>
      </div>
    </div>
  `;
    }

    function getUserAvatarUrl(phone) {
        return "http://localhost:8090/api/v1/user/phone/" + phone + "/avatar"
    }


    var loading = false;
    var page = 1;

    function loadVideos() {
        if (loading) {
            return;
        }
        loading = true;
        $('#loading').show();

        setTimeout(function() {
            $("#loading").hide();
        }, 1000)

        $.ajax({
            url: "http://localhost:8090/api/v1/hot/get",
            type: "get",
            success: function(res) {
                const hotStr = $.parseJSON(res);
                if(hotStr.status === 200) {

                    hotStr.data.forEach(function(item) {

                        console.log(item)

                        let username;
                        let avatarUrl;
                        let videoUrl;
                        const desc = item.description;
                        const likesCount = item.likes;
                        const commentsCount = item.comments;

                        $.ajax({
                            url: "http://localhost:8090/api/v1/video/getToken",
                            type: "get",
                            success: function (res) {
                                const tokenStr = $.parseJSON(res);
                                if(tokenStr.status === 200) {
                                    videoUrl = getDownloadUrl(tokenStr.data, item.path);

                                    $.ajax({
                                        url: "http://localhost:8090/api/v1/user/phone/" + item.creator,
                                        type: "get",
                                        success: function (res) {
                                            const userStr = $.parseJSON(res);
                                            if (userStr.status === 200) {
                                                username = userStr.data.nickname;
                                                avatarUrl = getUserAvatarUrl(userStr.data.phone);

                                                const element = createPostTemplate(username, avatarUrl, desc, videoUrl, likesCount, commentsCount);
                                                $("#videos").append(element);
                                            }
                                        }
                                    })
                                }
                            },
                        })
                    })

                }
            }
        })

        loading = false;
    }

    $(window).on('scroll', function() {
        var scrollHeight = $(document).height();
        var scrollPosition = $(window).height() + $(window).scrollTop();
        if ((scrollHeight - scrollPosition) / scrollHeight === 0) {
            loadVideos();
        }
    });

    loadVideos();
})