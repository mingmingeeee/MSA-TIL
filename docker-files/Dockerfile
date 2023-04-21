FROM mariadb:10.6
ENV MARIADB_ROOT_PASSWORD hotsix
ENV MARIADB_DATABASE test
COPY ./data /var/lib/mysql
EXPOSE 3306
CMD ["mysqld"]