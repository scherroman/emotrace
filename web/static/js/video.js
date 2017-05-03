/**
 * Created by romanscher on 5/3/17.
 */

$(document).ready(function() {

    // LOAD YOUTUBE VIDEO //

    // Get youtube video's id
    var video_id = $('#player').data('video-id');

    // 2. This code loads the IFrame Player API code asynchronously.
    var tag = document.createElement('script');

    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

    // 3. This function creates an <iframe> (and YouTube player)
    //    after the API code downloads.
    var player;
    window.onYouTubeIframeAPIReady =  function () {
        player = new YT.Player('player', {
            videoId: video_id,
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
    }

    // 4. The API will call this function when the video player is ready.
    function onPlayerReady(event) {
        event.target.playVideo();
    }

    // 5. The API calls this function when the player's state changes.
    //    YT.PlayerState.PLAYING indicates when a video starts playing (state=1)
    function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING) {}
    }
});

