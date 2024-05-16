package Admin;

//listen the WorkerProfilePage's onClose() method is being called or not
//if yes, it will call this onTableRefresh method
//it will also run this onTableRefresh method inside the ManageWorkerProfile, which will repopulate the updated data into the table
public interface TableRefreshListener {
    void onTableRefresh();
}
