create
    definer = root@`%` procedure testProcedure(IN rule int)
BEGIN
declare done_outer int default false;
    declare flag int default 1;
    declare fact_id int;

    declare fact_cursor cursor for
        select id_fact from facts_and_rules where id_rule = rule;

    declare continue handler for not found set done_outer = 1;

    open fact_cursor;
    now_loop: loop
        fetch fact_cursor into fact_id;
        if done_outer = 1 then leave now_loop;
        end if;
        select name from working_memory where id_fact = fact_id;
    end loop now_loop;
    close fact_cursor;
end;

