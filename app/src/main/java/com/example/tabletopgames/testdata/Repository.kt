package com.example.tabletopgames.testdata


class Repository {
    /*
    private val testData = TestData()
    var reservations:List<Reservation>? = null
    var dndLogSheets : List<DndAlLogSheet>? = null
    var mtgLogSheets : List<MTGlogsheet>? = null
    var monopLogSheets : List<MonopolyLogSheet>? = null
    var profile : Profile? = null
    var login : LoginModel? = null

    init {
        // using test data
        testData.buildTestData()
        reservations = testData.testListOfReservations
        dndLogSheets = testData.testListOfDndLogSheets
        mtgLogSheets = testData.testListOfMtgLogSheets
        monopLogSheets = testData.testListOfMonopolyLogSheets
    }


    fun loginFunction(email: String, password: String):Boolean{
        var login = false
        //search realm for this email/password combo
        //if found get this user's profile
        if (loginLookup(email,password)) {
            login = true
            getProfile()
        }else{
            error("Email and Password combination not found.")
        }
        return login
    }

    private fun loginLookup(email: String, password: String):Boolean{
        var found = false
        var fetchedLogin : LoginModel? = null
        // get Login from Realm where
        // email == email && password == password
        // fetchedLogin = REALM RETURN DATA(Login object model)
        if(fetchedLogin!=null) login = fetchedLogin
         else {
             if (email == testData.testLogin.email && password == testData.testLogin.password){
                 login = testData.testLogin
                 profile = testData.testProfile
             }
             found = true
        }
        return found
    }

    private fun getProfile() {
        var fetchedProfile : Profile? = null
        //  get Profile from REALM where
        //  email == login.email
        //  fetchedProfile = REALM RETURN DATA(Profile object model)
         if(fetchedProfile!=null) {
             profile = fetchedProfile
             if (profile!!.id!=testData.testProfile.id)getData(profile!!.id)
         }
        //  using test data
        //  no change
    }

    private fun getData(profileID: String){
        //build the data associated with this profileID
        getReservations(profileID)
        getLogSheets(profileID)
    }

    private fun getReservations(profileID: String){
        //get all reservations from REALM where
        //profileID = profileID

        //using test data
        //no change
    }

    private fun getLogSheets(profileID: String){
        getDNDlogsheets(profileID)
        getMTGlogsheets(profileID)
        getMonopolyLogsheets(profileID)
        //using test data
        //no change
    }

    private fun getDNDlogsheets(profileID: String){
        //get all dnd logsheets from REALM where
        //profileID = profileID

        //using test data
        //no change
        return
    }

    private fun getMTGlogsheets(profileID: String){
        //get all mtg logsheets from REALM where
        //profileID = profileID

        //using test data
        //no change
    }

    private fun getMonopolyLogsheets(profileID: String){
        //get all monopoly logsheets from REALM where
        //profileID = profileID

        //using test data

    }

*/
}