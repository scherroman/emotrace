/**
 * Created by romanscher on 5/14/17.
 *
 * JS handlers for graphing emotion trace data via Plotly
 */

// Representative colors for each emotion to graph
var emotion_colors = {
    joy: "rgb(44, 160, 44)",        //green
    sadness: "rgb(31, 119, 180)",   //blue
    disgust: "rgb(140, 86, 75)",    //brown
    contempt: "rgb(250, 238, 28)",  //yellow
    anger: "rgb(214, 39, 40)",      //red
    fear: "rgb(148, 103, 189)",     //purple
    surprise: "rgb(255, 127, 14)",  //orange
    valence: "rgb(127, 127, 127)",  //gray
    engagement: "rgb(188, 189, 34)" //gold
    //rgb(227, 119, 194) //pink
}


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

                if (raw_emotions.length > 0) {
                    raw_emotions.sort(function(a, b) {
                        return a.timestamp - b.timestamp;
                    });

                    var emo_traces = [];
                    var timestamps = raw_emotions.map(function(emo) {return emo.timestamp;});

                    // Create a trace (line) for each emotion
                    var first_raw_emotion = raw_emotions[0];
                    for (var property in first_raw_emotion) {
                        if (first_raw_emotion.hasOwnProperty(property)) {
                            if (['joy','sadness', 'disgust', 'contempt', 'anger',
                                    'fear', 'surprise', 'valence', 'engagement'].includes(property)) {

                                var emo_scores = raw_emotions.map(function(emo) {return Math.floor(emo[property]);});
                                var emo_trace = {
                                    x: timestamps,
                                    y: emo_scores,
                                    name: property,
                                    line: {
                                        color: emotion_colors[property]
                                    }
                                };
                                emo_traces.push(emo_trace)
                            }
                        }
                    }

                    var layout = {
                        xaxis: {
                            title: 'Time (sec)'
                        },
                        yaxis: {
                            title: 'Score'
                        },
                        margin: {t: 0}
                    };

                    // Redraws entire graph
                    Plotly.newPlot('emotrace-plots', emo_traces, layout);
                }
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
