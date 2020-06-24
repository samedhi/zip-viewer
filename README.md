# Clojure.zip Viewer

## Usage

[Live Version of the Clojure.Zip/Viewer](https://clojure-zip-viewer.web.app/)

This is an online application that allows you interactively manipulate a tree (really any clojure data structure) with [clojure.zip](https://clojuredocs.org/clojure.zip). I made this to help with my understanding of the `clojure.zip` namespace.

![Constructor](https://clojure-zip-viewer.web.app/img/constructors-2020-06-24-10-27-08.png)

Upon clicking the above link, you will be presented with a list of `clojure.zip` constructors. Paste the data structure in the appropriate constructor. As an example, try pasting `[1 2 [3 4] 5]` in the `(vector-zip nil)` constructor's input. The section will turn blue to indicate that it may be clicked. Go ahead and click on `(vector-zip [1 2 [3 4] 5])`.

![Actions](https://clojure-zip-viewer.web.app/img/actions-2020-06-24-10-29-04.png)

The view will transform to the functions that view/move/modify a `clojure.zip` loc. There is a history at the bottom that shows the functions that have been called. The currently selected history row is bold. You can change the currently selected history by clicking upon a row (undo/redo). You can also view the raw loc by clicking the chevron on the left of each row.

That's about it, hope it helps with visualizing `clojure.zip` functionality. 

## Contributions

Pull Request are welcome.

Every function in `clojure.zip` is supported except for `(edit)` & `(zipper)` (see [Issue #3](https://github.com/samedhi/zip-viewer/issues/3)). There is also an issue with using `(seq-zip)` as the reader will not read quoted values (see [Issue #2](https://github.com/samedhi/zip-viewer/issues/2)). Any help on these problems is greatly appreciated as it would allow full coverage of the `clojure.zip` namespace.

Also, if you have ideas about how to make the UI better (or any ideas in general), I appreciate constructive feedback.

Thank you.

## License
[MIT](https://choosealicense.com/licenses/mit/)
