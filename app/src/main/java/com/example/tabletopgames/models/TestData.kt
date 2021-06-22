package com.example.tabletopgames.models

class TestData {
    /*
    var testLogin = LoginModel("test@gmail.com","password")
    var testProfile = Profile("1","Test","User",
        "test@gmail.com","8178675309")
    var testListOfReservations = mutableListOf<Reservation>()
    var testListOfDndLogSheets = mutableListOf<DndAlLogSheet>()
    var testListOfMtgLogSheets = mutableListOf<MTGlogsheet>()
    var testListOfMonopolyLogSheets = mutableListOf<MonopolyLogSheet>()

    fun buildTestData(){
        buildReservationList()
        buildDndLogSheetsList()
        buildMtgLogSheetsList()
        buildMonopolyLogSheetsList()
    }

    private    fun buildReservationList(){
            testListOfReservations.add(
                Reservation("1","1",GameType().DND,
                    "1 January 2021","7:00 PM","1",
                    "1","1"))
            testListOfReservations.add(Reservation("2","1",GameType().DND,
                "2 January 2021","6:00 PM","2",
                "2","2"))
            testListOfReservations.add(Reservation("3","1",GameType().MTG,
                "3 January 2021","5:00 PM","3",
                "3","3"))
            testListOfReservations.add(Reservation("4","1",GameType().MONOP,
                "4 January 2021","4:00 PM","4",
                "4","4"))
        }

    private    fun buildDndLogSheetsList(){
        var testClassesANDLevels = mutableListOf<ClassesANDLevels>()
        testClassesANDLevels.add(ClassesANDLevels("1","1","Fighter","1"))
        testClassesANDLevels.add(ClassesANDLevels("2","1","Rogue","1"))
        testListOfDndLogSheets.add(DndAlLogSheet("1","1","12345678910",
        "Human",testClassesANDLevels,"none","none","none",getDndEntries("1")))
        testClassesANDLevels.clear()
        testClassesANDLevels.add(ClassesANDLevels("1","2","Fighter","1"))
        testClassesANDLevels.add(ClassesANDLevels("2","2","Cleric","1"))
        testListOfDndLogSheets.add(DndAlLogSheet("2","1","12345678910",
        "Dwarf",testClassesANDLevels,"none","none","none",getDndEntries("2"))
        )
        testClassesANDLevels.clear()
        testClassesANDLevels.add(ClassesANDLevels("1","3","Wizard","1"))
        testClassesANDLevels.add(ClassesANDLevels("2","3","Wizard","2"))
        testListOfDndLogSheets.add(
            DndAlLogSheet("3","1","12345678910",
        "Elf",testClassesANDLevels,"none","none","none",getDndEntries("3"))
        )
        testClassesANDLevels.clear()
    }

    private fun getDndEntries(logsheetID: String): MutableList<DndAlEntry> {
        var dndEntryList = mutableListOf<DndAlEntry>()
        for (i in 0 until (logsheetID.toInt()+3)){
            dndEntryList.add(DndAlEntry((i+1).toString(),logsheetID,"1",
                "Test Adventure","1 January 2021","testDM",
                "1",(i*1000).toString(),(i*2).toString(),(i*3).toString(),
                "Y",(i*1000).toString(),(i).toString(),(i+1).toString(),i.toString(),
                (i*1000).toString(),((i+1)*2).toString(),((i+2)*2).toString(),
                "This "+(i+1)+"was a blast!",i.toString(),i.toString()))
        }
        return dndEntryList
    }

    private    fun buildMtgLogSheetsList(){
        testListOfMtgLogSheets.add(MTGlogsheet("1","1",getTestPlayerList(),
            "1 January 2021",getMtgEntries("1",getTestPlayerList())))
        testListOfMtgLogSheets.add(MTGlogsheet("2","1",getTestPlayerList(),
            "1 January 2021",getMtgEntries("2",getTestPlayerList())))
        testListOfMtgLogSheets.add(MTGlogsheet("3","1",getTestPlayerList(),
            "1 January 2021",getMtgEntries("3",getTestPlayerList())))
    }

    private fun getMtgEntries   (logsheetID: String,testPlayerList:List<String>): MutableList<MtgEntry> {
        var mtgEntryList = mutableListOf<MtgEntry>()

        for (i in 1 until (logsheetID.toInt()+1)){
            var winnerList = mutableListOf<String>()
            for (j in 1 until 3){
                winnerList.add(testPlayerList[j])
            }
            mtgEntryList.add(MtgEntry(i.toString(),logsheetID,winnerList))
        }

        return mtgEntryList
    }

    private fun getTestPlayerList(): List<String> {
        var testListOfPlayers = listOf<String>()
        testListOfPlayers.plus("Jon Donnson")
        testListOfPlayers.plus("Judy Patutti")
        testListOfPlayers.plus("Claude Bawls")
        testListOfPlayers.plus("I.P. Freehly")
        return testListOfPlayers
    }

    private    fun buildMonopolyLogSheetsList(){
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("1","1",
            getTestPlayerList(),getMonopolyEntries("1",getTestPlayerList())))
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("2","1",
                getTestPlayerList(),getMonopolyEntries("2",getTestPlayerList())))
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("3","1",
                getTestPlayerList(),getMonopolyEntries("3",getTestPlayerList())))
    }

    private fun getMonopolyEntries(logsheetID: String,testPlayerList:List<String>): MutableList<MonopolyEntry> {
        var monopEntryList = mutableListOf<MonopolyEntry>()

        for (i in 1 until testPlayerList.size){
            var winner = testPlayerList[i]
            monopEntryList.add(MonopolyEntry(i.toString(),logsheetID,winner))
        }

        return monopEntryList
    }
    */
}