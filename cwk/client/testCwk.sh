

echo "------- SUCCESSFUL ADDITIONS -------"
java Client item table
java Client item vase
java Client item house

echo "------- FAIL ADDITION -------"
java Client item table

echo "------- SHOW FUNCTION BEFORE BID -------"
java Client show

echo "------- SUCCESSFUL BIDS -------"
java Client bid table 5
java Client bid vase 100
java Client bid table 10
java Client bid house 250000

echo "------- FAILED BID -------"
java Client bid antiqueMirror 100

echo "------- REJECTED BID -------"
java Client bid table 2

echo "------- FINAL SHOW -------"
java Client show