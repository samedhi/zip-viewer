- Clojure.zip Viewer

- A way of viewing clojure data structures with clojure.zip
- Separate views
    - A undo/redo history view that lets you see your changes and figure out what you want to do.
    - The actual data structure with the current focus and path to focus highlighted (the clojure.zip/root of the loc).
    - The clojure.zip data structure itself with matching color coding to that on the original data structure.
        - Option to either be "compact" or "exploded".
    - A set of actions you can take from your current focus.
        - Up
        - Down
        - Left
        - Right
        - Replace
- Implementation
    - clojure.zip
    - re-frame
Both to render to the screen and also because of its event system. The event system should allow me to trivially undo/redo with a simple history of all events.
    - garden
I have limited experience with this, but I would be interested in using it to try to style (colorize, bold, whatever) the text and get the spacing to occur for the exploded view. Maybe css flexbox or grid would help with this?
