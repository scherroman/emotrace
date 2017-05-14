/**
 * Created by romanscher on 5/14/17.
 */

$(document).ready(function() {

    // Load Emotion Trace handler
    $(document).on('click', '#load-emotion-trace', function() {
        var load_button = $(this);
        load_button.wrap("<fieldset disabled></fieldset>");
        var url = $(this).data('url');

        // Query server for Raw Emotion data
        // Upon Success: Load plotly graph with raw emotion data
        $.ajax({
            url: url,
            type: 'GET',
            dataType: "json",
            success: function(response) {
                var raw_emotions = JSON.parse(response.raw_emotions);

                console.log(response);
                console.log(raw_emotions);

                // TODO: Plot Raw Emotion data
                // Below is a test
                Plotly.plot('emotrace-plots', [{
                        x: [1, 2, 3, 4, 5],
                        y: [1, 2, 4, 8, 16]
                    }],
                    {margin: {t: 0}}
                );
            },
            error: function(error) {
                console.log(error);
                alert(error.statusText)
            },
            complete: function(data) {
                load_button.unwrap();
                load_button.text("Reload Emotion Trace");
            }
        });
    });
});
