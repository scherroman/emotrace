/**
 * Created by romanscher on 5/14/17.
 */

$(document).ready(function() {
    // Load Emotion Trace handler
    $(document).on('click', '#load-emotion-trace', function() {
        console.log("hey");

        Plotly.plot('emotrace-plots', [{
                x: [1, 2, 3, 4, 5],
                y: [1, 2, 4, 8, 16]
            }],
            {margin: {t: 0}}
        );
    });
});
