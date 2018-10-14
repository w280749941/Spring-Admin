package com.heartiger.admin.service;

import javax.persistence.EntityManager;
import java.util.List;

public class PageableService<T> {

    private int currentPage;
    private int maxResults;
    private int pageSize;
    private Class<T> clazz;

    private EntityManager entityManager;

    public PageableService(EntityManager entityManager, Class<T> clazz){
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getMaxPages() {
        return maxResults / pageSize;
    }

    public void init(int pageSize) {
        this.pageSize = pageSize;
        maxResults = ((Long) entityManager.createQuery("SELECT COUNT(e) FROM " + clazz.getName() + " e")
                .getSingleResult()).intValue();
        currentPage = 0;
    }

    public List getCurrentResults() {
        return entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e")
                .setFirstResult(currentPage * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public void next() {
        currentPage++;
    }

    public void previous() {
        currentPage--;
        if (currentPage < 0) {
            currentPage = 0;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage < 0 ? 0 : currentPage;
    }
}
